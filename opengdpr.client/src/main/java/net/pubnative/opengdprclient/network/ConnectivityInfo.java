package net.pubnative.opengdprclient.network;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;

/**
 * Created by erosgarciaponte on 08.01.18.
 */

public class ConnectivityInfo {

    public enum Connectivity {
        ETHERNET("ethernet"),
        WIFI("wifi"),
        WWAN("wwan"),
        NONE("none");

        private final String mConnectivity;

        Connectivity(String connectivity) {
            mConnectivity = connectivity;
        }

        @Override
        public String toString() {
            return mConnectivity;
        }
    }

    private static final String TAG = ConnectivityInfo.class.getSimpleName();
    private final Context mContext;
    private final ConnectivityManager mConnectivityManager;

    public ConnectivityInfo(Context context) {
        mContext = context.getApplicationContext();
        mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private boolean checkPermission(String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        return mContext.checkPermission(permission, Process.myPid(), Process.myUid())
                != PackageManager.PERMISSION_GRANTED;
    }

    public Connectivity getConnectivity() {
        if (mConnectivityManager == null || checkPermission(Manifest.permission.ACCESS_NETWORK_STATE)) {
            return Connectivity.NONE;
        }

        final NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return Connectivity.NONE;
        }

        switch (activeNetworkInfo.getType()) {
            case ConnectivityManager.TYPE_ETHERNET:
                return Connectivity.ETHERNET;
            case ConnectivityManager.TYPE_WIFI:
                return Connectivity.WIFI;
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                return Connectivity.WWAN;
            default:
                return Connectivity.NONE;
        }
    }
}