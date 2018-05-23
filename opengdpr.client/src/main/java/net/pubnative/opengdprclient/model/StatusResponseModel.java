package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class StatusResponseModel extends JsonModel {
    @BindField
    private String controller_id;
    @BindField
    private String expected_completion_time;
    @BindField
    private String subject_request_id;
    @BindField
    private String request_status;
    @BindField
    private String api_version;
    @BindField
    private String results_url;

    public StatusResponseModel() {
    }

    public StatusResponseModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getControllerId() {
        return controller_id;
    }

    public void setControllerId(String controllerId) {
        this.controller_id = controllerId;
    }

    public String getExpectedCompletionTime() {
        return expected_completion_time;
    }

    public void setExpectedCompletionTime(String expectedCompletionTime) {
        this.expected_completion_time = expectedCompletionTime;
    }

    public String getSubjectRequestId() {
        return subject_request_id;
    }

    public void setSubjectRequestId(String subjectRequestId) {
        this.subject_request_id = subjectRequestId;
    }

    public String getRequestStatus() {
        return request_status;
    }

    public void setRequestStatus(String requestStatus) {
        this.request_status = requestStatus;
    }

    public String getApiVersion() {
        return api_version;
    }

    public void setApiVersion(String apiVersion) {
        this.api_version = apiVersion;
    }

    public String getResultsUrl() {
        return results_url;
    }

    public void setResultsUrl(String resultsUrl) {
        this.results_url = resultsUrl;
    }
}
