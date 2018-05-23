package net.pubnative.opengdprclient.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import net.pubnative.opengdprclient.util.Crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

public class HttpRequest {
    private static final String TAG = HttpRequest.class.getSimpleName();

    private int mTimeoutInMillis = 4000; // 4 seconds
    private String mBody = null;
    private Map<String, String> mHeaders = null;
    // Inner
    private Listener mListener = null;
    private Handler mHandler = null;
    private ConnectivityInfo mDeviceInfo = null;

    public interface Method {
        String GET = "GET";
        String POST = "POST";
        String DELETE = "DELETE";
    }

    public interface Listener {

        /**
         * Called when the HttpRequest has just finished with a valid String response
         *
         * @param request request that have just finished
         * @param result  string with the given response from the server
         */
        void onPNHttpRequestFinish(HttpRequest request, String result);

        /**
         * Called when the HttpRequest fails, after this method the request will be stopped
         *
         * @param request   request that have just failed
         * @param exception exception with more info about the error
         */
        void onPNHttpRequestFail(HttpRequest request, Exception exception);
    }

    /**
     * Sets timeout for connection and reading, if not specified default is 0 ms
     *
     * @param timeoutInMillis time in milliseconds
     */
    public void setTimeout(int timeoutInMillis) {
        mTimeoutInMillis = timeoutInMillis;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public void start(Context context, final String method, final String urlString, Listener listener) {
        mDeviceInfo = new ConnectivityInfo(context);
        mListener = listener;
        mHandler = new Handler(Looper.getMainLooper());
        if (mListener == null) {
            Log.w(TAG, "Warning: null listener specified, performing request without callbacks");
        }
        if (context == null) {
            invokeFail(new IllegalArgumentException("HttpRequest - Error: null context provided, dropping call"));
        } else if (TextUtils.isEmpty(urlString)) {
            invokeFail(new IllegalArgumentException("HttpRequest - Error: null or empty url, dropping call"));
        } else if (!validateMethod(method)) {
            invokeFail(new IllegalArgumentException("HttpRequest - Error: Unsupported HTTP method, dropping call"));
        } else if (mDeviceInfo.getConnectivity() != ConnectivityInfo.Connectivity.NONE) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    doRequest(method, urlString);
                }
            }).start();
        } else {
            invokeFail(new Exception("Internet connection is not available"));
        }
    }

    private boolean validateMethod(String method) {
        if (TextUtils.isEmpty(method)) {
            return false;
        }
        switch (method.toUpperCase(Locale.ENGLISH)) {
            case Method.GET:
            case Method.POST:
            case Method.DELETE:
                return true;
            default:
                return false;
        }
    }

    protected void doRequest(String method, String urlString) {
        HttpURLConnection connection = null;
        // To avoid changing the POST string
        // during sending - make a local variable
        String bodyJson = mBody;
        Map<String, String> headers = mHeaders;
        try {
            // 1. Create connection
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            // 2. Set connection properties
            connection.setDoInput(true);
            connection.setConnectTimeout(mTimeoutInMillis);
            connection.setRequestMethod(method);

            if (headers != null && !headers.isEmpty()) {
                for (String header : headers.keySet()) {
                    connection.setRequestProperty(header, headers.get(header));
                }
            }

            if (!TextUtils.isEmpty(bodyJson)) {
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(bodyJson.getBytes().length);
                connection.setRequestProperty("Content-Length", Integer.toString(bodyJson.getBytes().length));
                connection.setRequestProperty("Content-MD5", Crypto.md5(bodyJson));
                OutputStream connectionOutputStream = connection.getOutputStream();
                OutputStreamWriter wr = new OutputStreamWriter(connectionOutputStream, "UTF-8");
                wr.write(bodyJson);
                wr.flush();
                wr.close();
            }

            // 3. Do request
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (isHttpSuccess(responseCode)) {
                try {
                    InputStream inputStream = connection.getInputStream();
                    String result = stringFromInputStream(inputStream);
                    inputStream.close();
                    invokeFinish(result);
                } catch (Exception ex) {
                    invokeFail(ex);
                }
            } else {
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append(String.format(Locale.ENGLISH, "status: %d", responseCode));
                errorMessage.append("\n");
                try {
                    String error = String.format(Locale.ENGLISH, "message: %s", stringFromInputStream(connection.getErrorStream()));
                    errorMessage.append(error);
                } catch (Exception ex) {
                    String error = String.format(Locale.ENGLISH, "parsingException: %s", ex.toString());
                    errorMessage.append(error);
                }
                invokeFail(new Exception(errorMessage.toString()));
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            invokeFail(new Exception("Not enough memory for making request!", outOfMemoryError));
        } catch (Exception exception) {
            invokeFail(exception);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    protected boolean isHttpSuccess(int responseCode) {
        return responseCode / 100 == 2;
    }

    protected String stringFromInputStream(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return "";
        }
        String result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length;
        try {
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            byteArrayOutputStream.flush();
            result = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "stringFromInputStream - Error:" + e);

            if (result == null) {
                result = byteArrayOutputStream.toString();
            }

            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append(String.format(Locale.ENGLISH, "serverResponse: %s", result));
            errorMessage.append("\n");
            errorMessage.append(String.format(Locale.ENGLISH, "IOException: %s", e.toString()));

            throw new Exception(errorMessage.toString());
        }
        return result;
    }

    protected void invokeFinish(final String result) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {

                if (mListener != null) {
                    mListener.onPNHttpRequestFinish(HttpRequest.this, result);
                }
                mListener = null;
            }
        });
    }

    protected void invokeFail(final Exception exception) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {

                if (mListener != null) {
                    mListener.onPNHttpRequestFail(HttpRequest.this, exception);
                }
                mListener = null;
            }
        });
    }
}
