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
package net.pubnative.opengdprclient.model;

import net.pubnative.opengdprclient.util.json.BindField;
import net.pubnative.opengdprclient.util.json.JsonModel;

import org.json.JSONObject;

public class DiscoveryIdentityModel extends JsonModel {
    @BindField
    private String identity_type;
    @BindField
    private String identity_format;

    public DiscoveryIdentityModel() {
    }

    public DiscoveryIdentityModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getIdentityType() {
        return identity_type;
    }

    public void setIdentityType(String identityType) {
        this.identity_type = identityType;
    }

    public String getIdentityFormat() {
        return identity_format;
    }

    public void setIdentityFormat(String identityFormat) {
        this.identity_format = identityFormat;
    }
}
