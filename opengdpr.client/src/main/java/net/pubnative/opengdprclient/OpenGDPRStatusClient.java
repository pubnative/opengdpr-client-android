package net.pubnative.opengdprclient;

import android.content.Context;
import android.text.TextUtils;

import net.pubnative.opengdprclient.model.StatusResponseModel;
import net.pubnative.opengdprclient.network.HttpRequest;
import net.pubnative.opengdprclient.util.Logger;
import net.pubnative.opengdprclient.util.OpenGDPRApiResponseListener;

import org.json.JSONObject;

public class OpenGDPRStatusClient {
    private static final String TAG = OpenGDPRStatusClient.class.getSimpleName();

    public interface StatusListener extends OpenGDPRApiResponseListener<StatusResponseModel> { }

    public void fetchRequestStatus(Context context, String requestId, final StatusListener listener) {
        if (TextUtils.isEmpty(requestId)) {
            listener.onFailure(new Exception("Invalid request id."));
        } else {
            String url = OpenGDPREndpoints.getStatusUrl(requestId);
            HttpRequest httpRequest = new HttpRequest();
            httpRequest.start(context, HttpRequest.Method.GET, url, new HttpRequest.Listener() {
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

    private void handleResponse(String result, StatusListener listener) {
        if (TextUtils.isEmpty(result)) {
            Exception exception = new Exception("Empty response received from server");
            Logger.e(TAG, exception.getMessage());
            listener.onFailure(exception);
        } else {
            try {
                StatusResponseModel model = new StatusResponseModel(new JSONObject(result));
                listener.onSuccess(model);
            } catch (Exception exception) {
                Logger.e(TAG, exception.getMessage());
                listener.onFailure(exception);
            }
        }
    }
}
