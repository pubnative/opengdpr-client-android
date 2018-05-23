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
