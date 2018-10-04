package com.softices.trainingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softices.trainingapp.DatabaseHelper;
import com.softices.trainingapp.R;
import com.softices.trainingapp.model.AppModel;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtMail, edtNumber, edtPassword;
    Button btnsavechange;
    DatabaseHelper databaseHelper;
    AppModel appModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();

        appModel = databaseHelper.getRecord();
        //old information of User
        edtName.setText(appModel.getUserName());
        edtMail.setText(appModel.getUserEmail());
        edtNumber.setText(String.valueOf(appModel.getUserNumber()));
        edtPassword.setText(appModel.getUserPassword());
    }

    @Override
    public void onClick(View v) {
        updateUser();
        Toast.makeText(UpdateActivity.this, "Data saved...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AccountDetailsActivity.class);
        startActivity(intent);
        finish();
    }

    public void init() {
        edtName = (EditText) findViewById(R.id.edt_update_name);
        edtMail = (EditText) findViewById(R.id.edt_update_email);
        edtNumber = (EditText) findViewById(R.id.edt_update_number);
        edtPassword = (EditText) findViewById(R.id.edt_update_password);
        btnsavechange = (Button) findViewById(R.id.btn_update_savechange);
        btnsavechange.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(this);
    }

    public void updateUser() {
        boolean updateRecord = databaseHelper.updateData(edtName.getText().toString(),
                edtMail.getText().toString(), edtNumber.getText().toString(),
                edtPassword.getText().toString());
        if (updateRecord == true) {
            Toast.makeText(UpdateActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(UpdateActivity.this, "Data is not Updated", Toast.LENGTH_SHORT).show();
        }
    }
}