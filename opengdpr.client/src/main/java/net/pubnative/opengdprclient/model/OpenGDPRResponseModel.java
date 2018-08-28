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
