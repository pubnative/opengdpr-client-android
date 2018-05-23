package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class OpenGDPRErrorResponseModel extends JsonModel {
    @BindField
    private ErrorModel error;

    public OpenGDPRErrorResponseModel() {
    }

    public OpenGDPRErrorResponseModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
