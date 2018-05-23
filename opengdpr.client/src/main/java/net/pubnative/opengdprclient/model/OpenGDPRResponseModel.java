package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class OpenGDPRResponseModel extends JsonModel {
    @BindField
    private String controller_id;
    @BindField
    private String expected_completion_time;
    @BindField
    private String received_time;
    @BindField
    private String encoded_request;
    @BindField
    private String subject_request_id;

    public OpenGDPRResponseModel() {
    }

    public OpenGDPRResponseModel(JSONObject jsonObject) throws Exception {
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

    public String getReceivedTime() {
        return received_time;
    }

    public void setReceivedTime(String receivedTime) {
        this.received_time = receivedTime;
    }

    public String getEncodedRequest() {
        return encoded_request;
    }

    public void setEncodedRequest(String encodedRequest) {
        this.encoded_request = encodedRequest;
    }

    public String getSubjectRequestId() {
        return subject_request_id;
    }

    public void setSubjectRequestId(String subjectRequestId) {
        this.subject_request_id = subjectRequestId;
    }
}
