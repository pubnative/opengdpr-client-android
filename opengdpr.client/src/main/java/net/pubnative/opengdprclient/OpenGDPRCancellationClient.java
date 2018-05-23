package net.pubnative.opengdprclient;

import android.content.Context;
import android.text.TextUtils;

import net.pubnative.opengdprclient.model.CancellationResponseModel;
import net.pubnative.opengdprclient.network.HttpRequest;
import net.pubnative.opengdprclient.util.Logger;
import net.pubnative.opengdprclient.util.OpenGDPRApiResponseListener;

import org.json.JSONObject;

public class OpenGDPRCancellationClient {
    private static final String TAG = OpenGDPRCancellationClient.class.getSimpleName();

    public interface CancellationListener extends OpenGDPRApiResponseListener<CancellationResponseModel> { }

    public void doCancellationRequest(Context context, String requestId, final CancellationListener listener) {
        if (TextUtils.isEmpty(requestId)) {
            listener.onFailure(new Exception("Invalid request id."));
        } else {
            String url = OpenGDPREndpoints.getCancellationUrl(requestId);
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.start(context, HttpRequest.Method.DELETE, url, new HttpRequest.Listener() {
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
        }
    }

    private void handleResponse(String result, CancellationListener listener) {
        if (TextUtils.isEmpty(result)) {
            Exception exception = new Exception("Empty response received from server");
            Logger.e(TAG, exception.getMessage());
            listener.onFailure(exception);
        } else {
            try {
                CancellationResponseModel model = new CancellationResponseModel(new JSONObject(result));
                listener.onSuccess(model);
            } catch (Exception exception) {
                Logger.e(TAG, exception.getMessage());
                listener.onFailure(exception);
            }
        }
    }
}
