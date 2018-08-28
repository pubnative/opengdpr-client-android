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
import android.widget.TextView;

import net.pubnative.opengdprclient.OpenGDPRDiscoveryClient;
import net.pubnative.opengdprclient.model.DiscoveryResponseModel;

public class DiscoveryActivity extends AppCompatActivity {
    private static final String TAG = DiscoveryActivity.class.getSimpleName();

    private TextView mResultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultView = findViewById(R.id.view_result);

        findViewById(R.id.button_fetch_discovery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDiscoveryRequest();
            }
        });
    }

    private void performDiscoveryRequest() {
        OpenGDPRDiscoveryClient client = new OpenGDPRDiscoveryClient();
        client.fetchDiscoveryData(this, new OpenGDPRDiscoveryClient.DiscoveryListener() {
            @Override
            public void onSuccess(DiscoveryResponseModel model) {
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
