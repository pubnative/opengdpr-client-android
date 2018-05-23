package net.pubnative.opengdpr.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.pubnative.opengdprclient.OpenGDPRDiscoveryClient;
import net.pubnative.opengdprclient.OpenGDPRStatusClient;
import net.pubnative.opengdprclient.model.DiscoveryResponseModel;
import net.pubnative.opengdprclient.model.StatusResponseModel;

public class StatusActivity extends AppCompatActivity {
    private static final String TAG = StatusActivity.class.getSimpleName();

    private TextView mResultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultView = findViewById(R.id.view_result);

        findViewById(R.id.button_check_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCheckStatus();
            }
        });
    }

    private void performCheckStatus() {
        OpenGDPRStatusClient client = new OpenGDPRStatusClient();
        client.fetchRequestStatus(this, Constants.SAMPLE_REQUEST_ID, new OpenGDPRStatusClient.StatusListener() {
            @Override
            public void onSuccess(StatusResponseModel model) {
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
