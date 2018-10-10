package com.softices.trainingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainingapp.database.DatabaseHelper;
import com.softices.trainingapp.R;

import static android.text.TextUtils.isEmpty;
import static android.view.View.OnClickListener;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    public EditText edtEmail, edtPassword;
    public TextView tvSignup;
    public Button btnLogin;
    private String email, password;
    private DatabaseHelper db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                validate();
                break;
            case R.id.tv_singup:
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
        }
    }

    private void savePreferences(String value, boolean key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sharedPreferences.edit();
        editor.putString("email", value);
        editor.putBoolean("is_login", key);
        editor.commit();
    }

    public void validate() {
        String mail = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (!(mail.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"))) {
            Toast.makeText(LoginActivity.this, getString(R.string.toast_error_mail), Toast.LENGTH_LONG).show();
        } else if (isEmpty(password)) {
            Toast.makeText(LoginActivity.this, getString(R.string.toast_error_password),
                    Toast.LENGTH_SHORT).show();
        } else {
            Boolean isLogin = db.checkUsr(edtEmail.getText().toString().trim(),
                    edtPassword.getText().toString().trim());
            if (isLogin) {
                savePreferences(email, true);
                Toast.makeText(LoginActivity.this, getString(R.string.toast_login_success),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Email and password not found.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvSignup = (TextView) findViewById(R.id.tv_singup);
        db = new DatabaseHelper(this);
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }
}