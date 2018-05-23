package net.pubnative.opengdprclient;

import android.net.Uri;

public class OpenGDPREndpoints {
    private static final String SCHEME = "https";
    private static final String BASE_URL = "opengdpr-api.herokuapp.com";
    private static final String API_PATH = "api";
    private static final String API_VERSION = "0.1.4";
    private static final String DISCOVERY = "discovery";
    private static final String REQUESTS = "opengdpr_requests";

    public static String getApiVersion() {
        return API_VERSION;
    }

    public static String getDiscoveryEndpoint() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(BASE_URL)
                .appendPath(API_PATH)
                .appendPath(API_VERSION)
                .appendPath(DISCOVERY)
                .build()
                .toString();
    }

    public static String getRequestEndpoint() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(BASE_URL)
                .appendPath(API_PATH)
                .appendPath(API_VERSION)
                .appendPath(REQUESTS)
                .build()
                .toString();
    }

    public static String getStatusUrl(String requestId) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(BASE_URL)
                .appendPath(API_PATH)
                .appendPath(API_VERSION)
                .appendPath(REQUESTS)
                .appendPath(requestId)
                .build()
                .toString();
    }

    public static String getCancellationUrl(String requestId) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(BASE_URL)
                .appendPath(API_PATH)
                .appendPath(API_VERSION)
                .appendPath(REQUESTS)
                .appendPath(requestId)
                .build()
                .toString();
    }
}
