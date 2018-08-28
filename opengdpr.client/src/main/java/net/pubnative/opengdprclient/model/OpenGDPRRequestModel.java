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
package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenGDPRRequestModel extends JsonModel {
    @BindField
    private String subject_request_id;
    @BindField
    private String subject_request_type;
    @BindField
    private List<RequestIdentityModel> subject_identities;
    @BindField
    private String submitted_time;
    @BindField
    private String api_version;
    @BindField
    private List<String> status_callback_urls;
    @BindField
    private String extensions;

    public OpenGDPRRequestModel() {
        this.subject_identities = new ArrayList<>();
        this.status_callback_urls = new ArrayList<>();
    }

    public OpenGDPRRequestModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getSubjectRequestId() {
        return subject_request_id;
    }

    public void setSubjectRequestId(String subjectRequestId) {
        this.subject_request_id = subjectRequestId;
    }

    public String getSubjectRequestType() {
        return subject_request_type;
    }

    public void setSubjectRequestType(String subjectRequestType) {
        this.subject_request_type = subjectRequestType;
    }

    public List<RequestIdentityModel> getSubjectIdentities() {
        return subject_identities;
    }

    public void setSubjectIdentities(List<RequestIdentityModel> subjectIdentities) {
        this.subject_identities = subjectIdentities;
    }

    public String getSubmittedTime() {
        return submitted_time;
    }

    public void setSubmittedTime(String submittedTime) {
        this.submitted_time = submittedTime;
    }

    public String getApiVersion() {
        return api_version;
    }

    public void setApiVersion(String apiVersion) {
        this.api_version = apiVersion;
    }

    public List<String> getStatusCallbackUrls() {
        return status_callback_urls;
    }

    public void setStatusCallbackUrls(List<String> statusCallbackUrls) {
        this.status_callback_urls = statusCallbackUrls;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }
}
