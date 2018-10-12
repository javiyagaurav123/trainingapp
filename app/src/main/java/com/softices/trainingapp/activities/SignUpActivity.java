package com.softices.trainingapp.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG="SignUpActiity";
public static final int SELECT_PICTURE=100;
    public Button btnSignUp;
    public ImageView ivUserProfile;
    public TextView tvAccount;
    public EditText edtName, edtEmail, edtMobileNumber, edtPassword, etdConfirmPassword;
    private DatabaseHelper dbHelper;
    public AppModel appModel;
    Bitmap myBitmap;
    Uri imageUri;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permission = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 107;

    public static final String password = "passwordkey";
    public static final String Email = "emailkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        permission.add(Manifest.permission.CAMERA);
        permissionsToRequest = findUnAskedPermission(permission);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
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
            case R.id.iv_user_profile_image:
                openImageChooser();
                startActivityForResult(getPickImageChooserIntent(), 200);
        }
    }

//    Boolean loadImageFromDB() {
//        try {
//            byte[] bytes = dbHelper.getImagefromDB();
//            dbHelper.close();
//            // Show Image from DB in ImageView
//            ivUserProfile.setImageBitmap(ImageUtiliti.getImage(bytes));
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//            dbHelper.close();
//            return false;
//        }
//    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void signUp() {
        String name = edtName.getText().toString();
        String mail = edtEmail.getText().toString();
        String mobileNumber = edtMobileNumber.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPaassword = etdConfirmPassword.getText().toString();

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

    private Uri getCaptureImageOutPutUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutPutUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
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
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select Image ");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
               }

                if (getPickImageResultUri(data) != null) {
                    imageUri = getPickImageResultUri(data);

                    try {
                        myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        myBitmap = rotateImageIfRequire(myBitmap, imageUri);
                        myBitmap = getResizedBitmap(myBitmap, 500);
                        ivUserProfile.setImageBitmap(myBitmap);

                    } catch (Exception e) {
                        Log.e(String.valueOf(this), "onActivityresult", e);
                    }
                } else {
                    bitmap = (Bitmap) data.getExtras().get("data");

                    myBitmap = bitmap;
//                ivUserProfile = findViewById(R.id.iv_user_profile_image);
                    if (ivUserProfile != null) {
                        ivUserProfile.setImageBitmap(myBitmap);
                    }
                    ivUserProfile.setImageBitmap(myBitmap);
                }

            }
        }

    private static Bitmap rotateImageIfRequire(Bitmap myBitmap, Uri selectedImage) throws IOException {

        ExifInterface exifInterface = new ExifInterface(selectedImage.getPath());
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(myBitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(myBitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(myBitmap, 270);
            default:
                return myBitmap;
        }
    }

    private static Bitmap rotateImage(Bitmap myBitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatrImage = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(),
                matrix, true);
        myBitmap.recycle();
        return rotatrImage;
    }

    private Bitmap getResizedBitmap(Bitmap myBitmap, int maxSize) {

        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(myBitmap, width, height, true);
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutPutUri() : data.getData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("Bitmap_uri", myBitmap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        myBitmap = savedInstanceState.getParcelable("Bitmap_uri");
    }

    private ArrayList<String> findUnAskedPermission(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String permission : wanted) {
            if (!hasPermission(permission)) {
                result.add(permission);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void setText() {
        appModel.setUserName(edtName.getText().toString().trim());
        appModel.setUserEmail(edtEmail.getText().toString().trim());
        appModel.setUserNumber(edtMobileNumber.getText().toString().trim());
        appModel.setUserPassword(edtPassword.getText().toString().trim());
        appModel.setUserImages(ivUserProfile.getImageMatrix().toString().getBytes());
    }

    @TargetApi(Build.VERSION_CODES.M)

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());

                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    public void init() {
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtMobileNumber = findViewById(R.id.edt_mobilenumber);
        edtPassword = findViewById(R.id.edt_password);
        ivUserProfile = findViewById(R.id.iv_user_profile_image);
        etdConfirmPassword = findViewById(R.id.edt_confirpass);
        btnSignUp = findViewById(R.id.btn_singup);
        tvAccount = findViewById(R.id.txt_account);
        dbHelper = new DatabaseHelper(this);
        appModel = new AppModel();
        btnSignUp.setOnClickListener(this);
        tvAccount.setOnClickListener(this);
        ivUserProfile.setOnClickListener(this);
    }
}