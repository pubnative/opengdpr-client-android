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
package net.pubnative.opengdprclient;

import android.net.Uri;

public class OpenGDPREndpoints {
    private static final String SCHEME = "https";
    private static final String BASE_URL = "opengdpr-api.herokuapp.com";
    private static final String API_PATH = "api";
    private static final String API_VERSION = "1.0";
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
