package com.softices.trainingapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class ConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("API123", "" + intent.getAction());

        if (intent.getAction().equals("com.softices.trainingapp.activities.SOME_ACTION"))
            Toast.makeText(context, "Some Action is Recevice", Toast.LENGTH_SHORT).show();
        else {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                try {
                    Toast.makeText(context, "Network is Connected", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Pleaces changed network or reconect it.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}