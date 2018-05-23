package net.pubnative.opengdpr.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.pubnative.opengdprclient.OpenGDPRCancellationClient;
import net.pubnative.opengdprclient.OpenGDPRDiscoveryClient;
import net.pubnative.opengdprclient.model.CancellationResponseModel;
import net.pubnative.opengdprclient.model.DiscoveryResponseModel;

public class CancelActivity extends AppCompatActivity {
    private static final String TAG = CancelActivity.class.getSimpleName();

    private TextView mResultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultView = findViewById(R.id.view_result);

        findViewById(R.id.button_cancel_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performCancelRequest();
            }
        });
    }

    private void performCancelRequest() {
        OpenGDPRCancellationClient client = new OpenGDPRCancellationClient();
        client.doCancellationRequest(this, Constants.SAMPLE_REQUEST_ID, new OpenGDPRCancellationClient.CancellationListener() {
            @Override
            public void onSuccess(CancellationResponseModel model) {
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
