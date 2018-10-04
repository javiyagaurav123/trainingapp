package com.softices.trainingapp.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.softices.trainingapp.R;

public class BroadcastReceiverActivity extends AppCompatActivity implements View.OnClickListener{

    ConnectionReceiver receiver;
    IntentFilter intentFilter;
    Button btnBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.softices.trainingapp.activities.BroadcastReceiverActivity.SOME ACTION");

        init();
    }

    @Override
    public void onClick(View v) {
        if (v== btnBroadcastReceiver){
            Intent intent=new Intent("com.softices.trainingapp.activities.BroadcastReceiverActivity.SOME ACTION");
            sendBroadcast(intent);
        }

    }
    public void init(){
        btnBroadcastReceiver =findViewById(R.id.btn_broadcastreceiver);
    }
}