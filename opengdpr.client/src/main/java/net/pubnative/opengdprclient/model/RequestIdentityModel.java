package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class RequestIdentityModel extends JsonModel {
    @BindField
    private String identity_type;
    @BindField
    private String identity_value;
    @BindField
    private String identity_format;

    public RequestIdentityModel() {
    }

    public RequestIdentityModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getIdentityType() {
        return identity_type;
    }

    public void setIdentityType(String identityType) {
        this.identity_type = identityType;
    }

    public String getIdentityValue() {
        return identity_value;
    }

    public void setIdentityValue(String identityValue) {
        this.identity_value = identityValue;
    }

    public String getIdentityFormat() {
        return identity_format;
    }

    public void setIdentityFormat(String identityFormat) {
        this.identity_format = identityFormat;
    }
}
