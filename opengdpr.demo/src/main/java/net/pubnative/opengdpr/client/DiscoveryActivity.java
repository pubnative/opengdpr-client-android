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
