package com.softices.trainingapp.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softices.trainingapp.R;
import com.softices.trainingapp.database.DatabaseHelper;
import com.softices.trainingapp.model.AppModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnSignUp;
    public ImageView ivUserProfileImage;
    public TextView tvAccount;
    public EditText edtName, edtEmail, edtMobileNumber, edtPassword, etdConfirmPassword;
    private DatabaseHelper dbHelper;
    public AppModel appModel;
    Bitmap myBitmap;
    Uri imageUri;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> getPermissionsToRejected = new ArrayList<>();
    private ArrayList<String> permission = new ArrayList<>();

    private static int ALL_PERMISSION_RESULT = 107;

    public static final String password = "passwordkey";
    public static final String Email = "emailkey";

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
            case R.id.iv_user_profile:
                startActivityForResult(getPickImageChooserIntent(), 200);
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

    public Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutPutUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureintent, 0);
        for (ResolveInfo resolveInfo : listCam) {
            Intent intent = new Intent(captureintent);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.
                    activityInfo.packageName));
            intent.setPackage(resolveInfo.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }


        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo resolveInfo : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.packageName));
            intent.setPackage(resolveInfo.activityInfo.packageName);
            allIntents.add(intent);
        }
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent:allIntents){
            if (intent.getComponent().getClassName().equals("com.android.documentui.DocumentActivity")){
                mainIntent=intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserInten=Intent.createChooser(mainIntent,"Select Image ");
        chooserInten.putExtra(Intent.EXTRA_INITIAL_INTENTS,allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserInten;
    }

    private Uri getCaptureImageOutPutUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (requestCode==Activity.RESULT_OK){

            if (getPickImageResultUri(data)!=null){
                imageUri=getPickImageResultUri(data);

                try{
                    myBitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public Uri getPickImageResultUri(Intent data){
        boolean isCamera=true;
        if (data != null){
            String action=data.getAction();
            isCamera= action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutPutUri() : data.getData();
    }

    public void init() {
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtMobileNumber = findViewById(R.id.edt_mobilenumber);
        edtPassword = findViewById(R.id.edt_password);
        etdConfirmPassword = findViewById(R.id.edt_confirpass);
        ivUserProfileImage = findViewById(R.id.iv_user_profile);
        btnSignUp = findViewById(R.id.btn_singup);
        tvAccount = findViewById(R.id.txt_account);
        dbHelper = new DatabaseHelper(this);
        appModel = new AppModel();
        btnSignUp.setOnClickListener(this);
        tvAccount.setOnClickListener(this);
        ivUserProfileImage.setOnClickListener(this);
    }

    public void setText() {
        appModel.setUserName(edtName.getText().toString().trim());
        appModel.setUserEmail(edtEmail.getText().toString().trim());
        appModel.setUserNumber(edtMobileNumber.getText().toString().trim());
        appModel.setUserPassword(edtPassword.getText().toString().trim());
    }
}