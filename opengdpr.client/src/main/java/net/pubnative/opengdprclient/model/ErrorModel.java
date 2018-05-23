package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

import java.util.List;

public class ErrorModel extends JsonModel {
    @BindField
    private String code;
    @BindField
    private String message;
    @BindField
    private List<ErrorItemModel> errors;

    public ErrorModel() {
    }

    public ErrorModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorItemModel> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorItemModel> errors) {
        this.errors = errors;
    }
}
