package com.softices.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.softices.trainingapp.R;

public class WebServiceActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGet, btnPost, btnPut, btnDelete;
    Toolbar toolbarWebServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        init();
    }

    @Override
    public void onClick(View v) {
        if (v == btnGet) {
            Intent intent = new Intent(WebServiceActivity.this, GetActivity.class);
            startActivity(intent);
        } else if (v == btnPost) {
            Intent intent1 = new Intent(WebServiceActivity.this, PostActivity.class);
            startActivity(intent1);
        } else if (v == btnPut) {
            Intent intent = new Intent(WebServiceActivity.this, PutActivity.class);
            startActivity(intent);
        } else if (v == btnDelete) {
            Intent intent = new Intent(WebServiceActivity.this, DeleteActivity.class);
            startActivity(intent);
        }
    }

    public void init() {
        toolbarWebServices = findViewById(R.id.toolbar_web_services);
        setSupportActionBar(toolbarWebServices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);
        btnPut = findViewById(R.id.btn_put);
        btnDelete = findViewById(R.id.btn_delete);
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnPut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
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