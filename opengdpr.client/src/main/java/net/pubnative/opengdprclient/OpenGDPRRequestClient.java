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
