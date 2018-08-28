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
package net.pubnative.opengdprclient.request;

import android.text.TextUtils;

import net.pubnative.opengdprclient.OpenGDPREndpoints;
import net.pubnative.opengdprclient.model.OpenGDPRRequestModel;
import net.pubnative.opengdprclient.model.RequestIdentityModel;
import net.pubnative.opengdprclient.model.RequestType;

import java.util.UUID;

public class OpenGDPRRequest {
    private final OpenGDPRRequestModel mRequestModel;

    public OpenGDPRRequest() {
        mRequestModel = new OpenGDPRRequestModel();
        mRequestModel.setSubjectRequestId(UUID.randomUUID().toString());
        mRequestModel.setApiVersion(OpenGDPREndpoints.getApiVersion());
    }

    public void setType(String type) {
        if (type.equals(RequestType.ACCESS) || type.equals(RequestType.PORTABILITY) || type.equals(RequestType.ERASURE)) {
            mRequestModel.setSubjectRequestType(type);
        }
    }

    public void setRequestId(String requestId) {
        if (!TextUtils.isEmpty(requestId)) {
            mRequestModel.setSubjectRequestId(requestId);
        }
    }

    public void setSubmittedTime(String rfc3339Date) {
        if (!TextUtils.isEmpty(rfc3339Date)) {
            mRequestModel.setSubmittedTime(rfc3339Date);
        }
    }

    public void addIdentity(RequestIdentityModel identityModel) {
        if (identityModel != null) {
            mRequestModel.getSubjectIdentities().add(identityModel);
        }
    }

    public void setExtensions(String extensions) {
        if (!TextUtils.isEmpty(extensions)) {
            mRequestModel.setExtensions(extensions);
        }
    }

    public void addCallbackUrl(String url) {
        if (!TextUtils.isEmpty(url) && !mRequestModel.getStatusCallbackUrls().contains(url)) {
            mRequestModel.getStatusCallbackUrls().add(url);
        }
    }

    public OpenGDPRRequestModel getModel() {
        return mRequestModel;
    }
}
