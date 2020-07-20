package com.petertacon.bikecontrol;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static String getConnectivityStatusString(Context contexto) {
        String status = null;
        ConnectivityManager ConnMan =
                (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = ConnMan.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "WiFi Activado";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Datos móviles activado";
                return status;
            }
        } else {
            status = "No hay conexión";
            return status;
        }
        return status;
    }
}
