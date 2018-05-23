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
