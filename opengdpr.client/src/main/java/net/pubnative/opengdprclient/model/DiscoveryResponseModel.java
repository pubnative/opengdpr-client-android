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

import java.util.List;

public class DiscoveryResponseModel extends JsonModel {
    @BindField
    private String api_version;
    @BindField
    private List<DiscoveryIdentityModel> supported_identities;
    @BindField
    private List<String> supported_subject_request_types;
    @BindField
    private String processor_certificate;

    public DiscoveryResponseModel() {
    }

    public DiscoveryResponseModel(JSONObject jsonObject) throws Exception {
        fromJson(jsonObject);
    }

    public String getApiVersion() {
        return api_version;
    }

    public void setApiVersion(String apiVersion) {
        this.api_version = apiVersion;
    }

    public List<DiscoveryIdentityModel> getSupportedIdentities() {
        return supported_identities;
    }

    public void setSupportedIdentities(List<DiscoveryIdentityModel> supportedIdentities) {
        this.supported_identities = supportedIdentities;
    }

    public List<String> getSupportedSubjectRequestTypes() {
        return supported_subject_request_types;
    }

    public void setSupportedSubjectRequestTypes(List<String> supportedSubjectRequestTypes) {
        this.supported_subject_request_types = supportedSubjectRequestTypes;
    }

    public String getProcessorCertificate() {
        return processor_certificate;
    }

    public void setProcessorCertificate(String processorCertificate) {
        this.processor_certificate = processorCertificate;
    }
}