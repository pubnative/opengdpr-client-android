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
