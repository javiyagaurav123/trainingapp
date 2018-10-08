package com.softices.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.softices.trainingapp.R;
import com.softices.trainingapp.services.MyService;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartServices, btnStopServices;
    Toolbar toolbarServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        init();
    }

    @Override
    public void onClick(View view) {
        if (view == btnStartServices) {
            startService(new Intent(this, MyService.class));
        } else if (view == btnStopServices) {
            stopService(new Intent(this, MyService.class));
        }
    }

    public void init() {
        toolbarServices=findViewById(R.id.toolbar_services);
        setSupportActionBar(toolbarServices);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnStartServices = findViewById(R.id.btn_start_services);
        btnStopServices = findViewById(R.id.btn_stop_services);
        btnStartServices.setOnClickListener(this);
        btnStopServices.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}