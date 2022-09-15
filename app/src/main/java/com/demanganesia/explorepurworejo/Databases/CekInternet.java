package com.demanganesia.explorepurworejo.Databases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CekInternet {

    public boolean jikaTersambungKeInternet (Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo koneksiWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo koneksiMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((koneksiWifi != null && koneksiWifi.isConnected()) || (koneksiMobile != null && koneksiMobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}
