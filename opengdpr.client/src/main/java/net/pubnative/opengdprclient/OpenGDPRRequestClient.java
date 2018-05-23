package net.pubnative.opengdprclient;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import net.pubnative.opengdprclient.model.OpenGDPRRequestModel;
import net.pubnative.opengdprclient.model.OpenGDPRResponseModel;
import net.pubnative.opengdprclient.network.HttpRequest;
import net.pubnative.opengdprclient.request.OpenGDPRRequest;
import net.pubnative.opengdprclient.util.DateUtil;
import net.pubnative.opengdprclient.util.Logger;
import net.pubnative.opengdprclient.util.OpenGDPRApiResponseListener;
import net.pubnative.opengdprclient.util.UrlUtils;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OpenGDPRRequestClient {
    private static final String TAG = OpenGDPRRequestClient.class.getSimpleName();

    public interface RequestListener extends OpenGDPRApiResponseListener<OpenGDPRResponseModel> { }

    public void doRequest(Context context, OpenGDPRRequest request, final RequestListener listener) {
        String url = OpenGDPREndpoints.getRequestEndpoint();
        request.setSubmittedTime(DateUtil.getRFC3339DateString(new Date()));

        try {
            String body = UrlUtils.createQueryStringForParameters(request.getModel().toJson());
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setBody(body);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            httpRequest.setHeaders(headers);

            httpRequest.start(context, HttpRequest.Method.POST, url, new HttpRequest.Listener() {
                @Override
                public void onPNHttpRequestFinish(HttpRequest request, String result) {
                    if (listener != null) {
                        handleResponse(result, listener);
                    }
                }

                @Override
                public void onPNHttpRequestFail(HttpRequest request, Exception exception) {
                    if (listener != null) {
                        listener.onFailure(exception);
                    }
                }
            });
        } catch (Exception exception) {
            Logger.e(TAG, exception.getMessage());
            listener.onFailure(exception);
        }
    }

    private void handleResponse(String result, RequestListener listener) {
        if (TextUtils.isEmpty(result)) {
            Exception exception = new Exception("Empty response received from server");
            Logger.e(TAG, exception.getMessage());
            listener.onFailure(exception);
        } else {
            try {
                OpenGDPRResponseModel model = new OpenGDPRResponseModel(new JSONObject(result));
                listener.onSuccess(model);
            } catch (Exception exception) {
                Logger.e(TAG, exception.getMessage());
                listener.onFailure(exception);
            }
        }
    }

    private String buildPostParams(OpenGDPRRequestModel model) throws Exception {
        JSONObject object = model.toJson();
        Uri.Builder builder = new Uri.Builder();
        return builder.build().getEncodedQuery();
    }
}
