package com.softices.trainingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainingapp.database.DatabaseHelper;
import com.softices.trainingapp.R;
import com.softices.trainingapp.model.AppModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnSignUp;
    public TextView tvAccount;
    public EditText edtName, edtEmail, edtMobileNumber, edtPassword, etdConfirmPassword;
    private DatabaseHelper dbHelper;
    public AppModel appModel;

    public static final String password = "passwordkey";
    public static final String Email = "emailkey";
//    public static final String MyPreferences = "Mypreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_singup:
                signUp();
                break;
            case R.id.txt_account:
                Toast.makeText(SignUpActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void signUp() {
        String name = edtName.getText().toString();
        String mail = edtEmail.getText().toString();
        String mobileNumber = edtMobileNumber.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPaassword = etdConfirmPassword.getText().toString();
        String getstring = getString(R.string.toast_error_name);

        if (name.length() < 4) {
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_error_name),
                    Toast.LENGTH_LONG).show();
        } else if (!(mail.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\." +
                "([a-zA-Z]{2,5})$"))) {
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_error_mail),
                    Toast.LENGTH_SHORT).show();
        } else if (mobileNumber.length() < 10) {
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_error_number),
                    Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_error_password),
                    Toast.LENGTH_SHORT).show();
        } else if (!(confirmPaassword.matches(password))) {
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_error_confirmpassword),
                    Toast.LENGTH_SHORT).show();
        } else {

            setText();
            dbHelper.insertRecord(appModel);
            Toast.makeText(SignUpActivity.this, getString(R.string.toast_message_singup),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void init() {
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtMobileNumber = findViewById(R.id.edt_mobilenumber);
        edtPassword = findViewById(R.id.edt_password);
        etdConfirmPassword = findViewById(R.id.edt_confirpass);
        btnSignUp = findViewById(R.id.btn_singup);
        tvAccount = findViewById(R.id.txt_account);
        dbHelper = new DatabaseHelper(this);
        appModel = new AppModel();
        btnSignUp.setOnClickListener(this);
        tvAccount.setOnClickListener(this);
    }

    public void setText() {
        appModel.setUserName(edtName.getText().toString().trim());
        appModel.setUserEmail(edtEmail.getText().toString().trim());
        appModel.setUserNumber(edtMobileNumber.getText().toString().trim());
        appModel.setUserPassword(edtPassword.getText().toString().trim());
    }
}