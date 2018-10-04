package com.softices.trainingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.softices.trainingapp.services.MyService;
import com.softices.trainingapp.R;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start_services, btn_stop_services;
    ImageView iv_Back_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        init();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_start_services) {
            startService(new Intent(this, MyService.class));
        } else if (view == btn_stop_services) {
            stopService(new Intent(this, MyService.class));
        } else if (view == iv_Back_Image) {
            Intent intent = new Intent(ServicesActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void init() {
        btn_start_services = findViewById(R.id.btn_start_services);
        btn_stop_services = findViewById(R.id.btn_stop_services);
        iv_Back_Image = findViewById(R.id.ic_btn_backimage);

        btn_start_services.setOnClickListener(this);
        btn_stop_services.setOnClickListener(this);
        iv_Back_Image.setOnClickListener(this);
    }
}