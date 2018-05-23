package net.pubnative.opengdprclient.util;

import net.pubnative.opengdprclient.util.json.JsonModel;

public interface OpenGDPRApiResponseListener<T extends JsonModel> {
    void onSuccess(T model);
    void onFailure(Throwable error);
}
