// The MIT License (MIT)
//
// Copyright (c) 2018 PubNative GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
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
