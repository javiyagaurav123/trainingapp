package com.softices.trainingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softices.trainingapp.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public String TAG = "DatabaseHelper";
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "TraineeApp.db";
    //Table name
    private static final String TABLE_USER = "User";
    // User table columns names
    public final String COLUMN_USER_NAME = "user_name";
    public final String COLUMN_USER_EMAIL = "user_email";
    public final String COLUMN_USER_MOBILENUMBER = "user_mobileNumber";
    public final String COLUMN_USER_PASSWORD = "usre_password";
    public final String COLUMN_USER_IMAGES = "user_image";

    private SQLiteDatabase database = null;

    private Cursor cursor;

    // Create table Query in database UserRecord
    private String CREATE_TABLE_USER = " CREATE TABLE "
            + TABLE_USER + "( "
            + COLUMN_USER_NAME + " text,"
            + COLUMN_USER_EMAIL + " text PRIMARY KEY,"
            + COLUMN_USER_MOBILENUMBER + " INTEGER,"
            + COLUMN_USER_PASSWORD + " text,"
            + COLUMN_USER_IMAGES + " BLOB NOT NULL)";

    // DROP TABLE IF EXISTS
    private String DROP_TABLE_USER = "DROP TABLE IF EXISTS" + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USER);
            System.out.print("Database table Created.....");
        } catch (Exception e) {
            Log.e(TAG, "onCreate " + e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertRecord(AppModel appModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //column of database table.
            contentValues.put(COLUMN_USER_NAME, appModel.getUserName());
            contentValues.put(COLUMN_USER_EMAIL, appModel.getUserEmail());
            contentValues.put(COLUMN_USER_PASSWORD, appModel.getUserPassword());
            contentValues.put(COLUMN_USER_MOBILENUMBER, appModel.getUserNumber());
            contentValues.put(COLUMN_USER_IMAGES, appModel.getUserImages(cursor.getString(4)));

            //insert data into Userdatabase
            db.insert(TABLE_USER, null, contentValues);
            db.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "insertRecord " + e);
            return false;
        }
    }

    // Update record in table
    public boolean updateData(String name, String email, String number, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, name);
        contentValues.put(COLUMN_USER_EMAIL, email);
        contentValues.put(COLUMN_USER_MOBILENUMBER, number);
        contentValues.put(COLUMN_USER_PASSWORD, password);
//        contentValues.put(COLUMN_USER_IMAGES,images);
        db.update(TABLE_USER, contentValues, "user_email = ?", new String[]{email});
        return true;
    }

    public boolean deleteRecord(AppModel appModel) {
        try {
            database = getWritableDatabase();
            database.delete(TABLE_USER, COLUMN_USER_EMAIL + " = ? ", new String[]{appModel.getUserEmail()});
            database.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "User Data Deleted" + e);
            return false;
        }
    }

    public AppModel getRecord() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from user", null);
        AppModel appModel = null;
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                appModel = new AppModel();
                appModel.setUserName(cursor.getString(0));
                appModel.setUserEmail(cursor.getString(1));
                appModel.setUserNumber(cursor.getString(2));
                appModel.setUserPassword(cursor.getString(3));
//                appModel.getUserImages(cursor.getString(4));
                Log.e("mye", "user email " + appModel.getUserEmail());
            }
        }
        cursor.close();
        database.close();
        return appModel;
    }

    public List<AppModel> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_IMAGES
        };
        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<AppModel> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                AppModel appModel = new AppModel();
                appModel.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                appModel.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                appModel.setUserPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                appModel.setUserImages(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_IMAGES)));
//                appModel.setUserNumber(cursor.getString(cursor.getColumnIndex(COLUMN_USER_MOBILENUMBER)));
                userList.add(appModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public boolean checkUsr(String email, String password) {
        String[] columns = {
//                COLUMN_USER_MOBILENUMBER
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "= ?" + " AND " + COLUMN_USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
}