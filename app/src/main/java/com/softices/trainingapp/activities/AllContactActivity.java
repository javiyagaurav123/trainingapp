package com.softices.trainingapp.activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.softices.trainingapp.R;
import com.softices.trainingapp.adapters.AllContactsAdapter;
import com.softices.trainingapp.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class AllContactActivity extends AppCompatActivity {

    RecyclerView rvContacts;
    Toolbar toobarContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contact);

        init();
        getAllContects();
    }

    private void getAllContects() {
        List<AppModel> appModelList = new ArrayList();
        AppModel appModel;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex
                        (ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    appModel = new AppModel();
                    appModel.setContactName(name);
                    Cursor phoneCursor = contentResolver.query(ContactsContract.
                            CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.
                            CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phoneCursor.close();
                    Cursor emailCursor = contentResolver.query(ContactsContract.CommonDataKinds.
                            Email.CONTENT_URI, null, ContactsContract.
                            CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex
                                (ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    appModelList.add(appModel);
                }
            }
        }
        Log.e("my", "list " + appModelList.size());
        AllContactsAdapter contactsAdapter = new AllContactsAdapter(appModelList, getApplicationContext());
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(contactsAdapter);
    }

    public void init() {
        rvContacts = findViewById(R.id.rv_contact);
        toobarContact = findViewById(R.id.toolbar_user_contact);
        setSupportActionBar(toobarContact);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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