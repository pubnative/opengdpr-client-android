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
package net.pubnative.opengdpr.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.pubnative.opengdprclient.OpenGDPRRequestClient;
import net.pubnative.opengdprclient.model.IdentityFormat;
import net.pubnative.opengdprclient.model.IdentityType;
import net.pubnative.opengdprclient.model.OpenGDPRResponseModel;
import net.pubnative.opengdprclient.model.RequestIdentityModel;
import net.pubnative.opengdprclient.model.RequestType;
import net.pubnative.opengdprclient.request.OpenGDPRRequest;
import net.pubnative.opengdprclient.util.Crypto;

public class RequestActivity extends AppCompatActivity {
    private static final String TAG = RequestActivity.class.getSimpleName();

    private TextView mResultView;
    private RadioGroup mRequestTypeGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultView = findViewById(R.id.view_result);
        mRequestTypeGroup = findViewById(R.id.group_request_type);

        findViewById(R.id.button_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRequest();
            }
        });
    }

    private void performRequest() {
        String mail = "test@mail.com";
        RequestIdentityModel identity1 = new RequestIdentityModel();
        identity1.setIdentityType(IdentityType.EMAIL);
        identity1.setIdentityFormat(IdentityFormat.RAW);
        identity1.setIdentityValue(mail);

        RequestIdentityModel identity2 = new RequestIdentityModel();
        identity2.setIdentityType(IdentityType.EMAIL);
        identity2.setIdentityFormat(IdentityFormat.MD5);
        identity2.setIdentityValue(Crypto.md5(mail));

        RequestIdentityModel identity3 = new RequestIdentityModel();
        identity3.setIdentityType(IdentityType.EMAIL);
        identity3.setIdentityFormat(IdentityFormat.SHA256);
        identity3.setIdentityValue(Crypto.sha256(mail));

        RequestIdentityModel identity4 = new RequestIdentityModel();
        identity4.setIdentityType(IdentityType.EMAIL);
        identity4.setIdentityFormat(IdentityFormat.SHA1);
        identity4.setIdentityValue(Crypto.sha1(mail));

        OpenGDPRRequest request = new OpenGDPRRequest();
        request.addIdentity(identity1);
        request.addIdentity(identity2);

        switch (mRequestTypeGroup.getCheckedRadioButtonId()) {
            case R.id.radio_access:
                request.setType(RequestType.ACCESS);
                break;
            case R.id.radio_portability:
                request.setType(RequestType.PORTABILITY);
                break;
            case R.id.radio_erasure:
                request.setType(RequestType.ERASURE);
                break;
            default:
                request.setType(RequestType.ACCESS);

        }

        OpenGDPRRequestClient client = new OpenGDPRRequestClient();
        client.doRequest(this, request, new OpenGDPRRequestClient.RequestListener() {
            @Override
            public void onSuccess(OpenGDPRResponseModel model) {
                try {
                    String content = model.toJson().toString();
                    mResultView.setText(content);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }
}
