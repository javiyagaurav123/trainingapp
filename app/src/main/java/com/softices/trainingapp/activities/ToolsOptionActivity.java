package com.softices.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.softices.trainingapp.R;

public class ToolsOptionActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView imageContact, imageBackContact, imageUerList, imageWebServices, imageBroadcastRecever;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_option);

        init();
    }

    @Override
    public void onClick(View v) {
        if (v == imageContact) {
            Intent intent = new Intent(ToolsOptionActivity.this, AllContactActivity.class);
            startActivity(intent);
        } else if (v == imageUerList) {
            Intent intent = new Intent(ToolsOptionActivity.this, UserListActivity.class);
            startActivity(intent);
        } else if (v == imageWebServices) {
            Intent intent = new Intent(ToolsOptionActivity.this, WebServiceActivity.class);
            startActivity(intent);
        } else if (v == imageBackContact) {
            Intent intent = new Intent(ToolsOptionActivity.this, DashboardActivity.class);
            startActivity(intent);
        } else if (v == imageBroadcastRecever) {
            Intent intent = new Intent(ToolsOptionActivity.this, BroadCastActivity.class);
            startActivity(intent);
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_option);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imageContact = findViewById(R.id.ic_contact);
        imageUerList = findViewById(R.id.ic_list_user);
        imageWebServices = findViewById(R.id.ic_web_service);
        imageBroadcastRecever = findViewById(R.id.ic_broadcast_recever);
        
        imageContact.setOnClickListener(this);
        imageBroadcastRecever.setOnClickListener(this);
        imageUerList.setOnClickListener(this);
        imageWebServices.setOnClickListener(this);
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