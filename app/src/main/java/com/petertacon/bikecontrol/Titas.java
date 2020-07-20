package com.petertacon.bikecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Titas extends BroadcastReceiver {
    public void onReceive(Context contexto, Intent intento){
        String status = NetworkUtil.getConnectivityStatusString(contexto);
        if (status.isEmpty()){
            status = "No hay conexión a Internet";
        }
        Toast.makeText(contexto, status, Toast.LENGTH_LONG).show();
    }
}
