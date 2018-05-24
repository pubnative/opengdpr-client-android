package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class CancellationResponseModel extends JsonModel {
    @BindField
    private String controller_id;
    @BindField
    private String received_time;
    @BindField
    private String subject_request_id;
    @BindField
    private String processor_signature;
    @BindField
    private String api_version;

    public CancellationResponseModel() {
    }

    public CancellationResponseModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getControllerId() {
        return controller_id;
    }

    public void setControllerId(String controllerId) {
        this.controller_id = controllerId;
    }

    public String getReceivedTime() {
        return received_time;
    }

    public void setReceivedTime(String receivedTime) {
        this.received_time = receivedTime;
    }

    public String getSubjectRequestId() {
        return subject_request_id;
    }

    public void setSubjectRequestId(String subjectRequestId) {
        this.subject_request_id = subjectRequestId;
    }

    public String getProcessorSignature() {
        return processor_signature;
    }

    public void setProcessorSignature(String processorSignature) {
        this.processor_signature = processorSignature;
    }

    public String getApiVersion() {
        return api_version;
    }

    public void setApiVersion(String apiVersion) {
        this.api_version = apiVersion;
    }
}
