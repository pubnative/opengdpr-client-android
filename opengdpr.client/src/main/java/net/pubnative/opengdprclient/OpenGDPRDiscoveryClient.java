package net.pubnative.opengdprclient;

import android.content.Context;
import android.text.TextUtils;

import net.pubnative.opengdprclient.model.DiscoveryResponseModel;
import net.pubnative.opengdprclient.network.HttpRequest;
import net.pubnative.opengdprclient.util.Logger;
import net.pubnative.opengdprclient.util.OpenGDPRApiResponseListener;

import org.json.JSONObject;

public class OpenGDPRDiscoveryClient {
    private static final String TAG = OpenGDPRDiscoveryClient.class.getSimpleName();

    public interface DiscoveryListener extends OpenGDPRApiResponseListener<DiscoveryResponseModel> {
    }

    public void fetchDiscoveryData(Context context, final DiscoveryListener listener) {
        String url = OpenGDPREndpoints.getDiscoveryEndpoint();
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.start(context, HttpRequest.Method.GET, url, new HttpRequest.Listener() {
            @Override
            public void onPNHttpRequestFinish(HttpRequest request, String result) {
                if (listener != null) {
                    handleDiscoveryResponse(result, listener);
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

    private void handleDiscoveryResponse(String result, DiscoveryListener listener) {
        if (TextUtils.isEmpty(result)) {
            Exception exception = new Exception("Empty response received from server");
            Logger.e(TAG, exception.getMessage());
            listener.onFailure(exception);
        } else {
            try {
                DiscoveryResponseModel model = new DiscoveryResponseModel(new JSONObject(result));
                listener.onSuccess(model);
            } catch (Exception exception) {
                Logger.e(TAG, exception.getMessage());
                listener.onFailure(exception);
            }
        }
    }
}
