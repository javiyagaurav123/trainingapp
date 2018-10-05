package com.softices.trainingapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softices.trainingapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PutActivity extends AppCompatActivity {

    TextView tvName, tvjob;
    EditText edtName, edtJob;
    Toolbar toolbarPut;
    Button btnParseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        init();
//        putData(edtName.getText().toString(),"adsasdsadsa");
    }

    public void putData(final String name, final String job) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String Url = "https://reqres.in/api/users/2";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("job", job);
                return params;
            }
        };
        queue.add(putRequest);
    }

    private void parseData(String response) {
        try {
            JSONObject jsonData = new JSONObject(response);

            String name=jsonData.getString("name");
            String job=jsonData.getString("job");

            tvName.setText("Name:" + name);
            tvjob.setText("job:" +job);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        tvName = findViewById(R.id.tv_name);
        tvjob = findViewById(R.id.tv_lname);
        edtName = findViewById(R.id.edt_name);
        edtJob = findViewById(R.id.edt_job);
        btnParseData = findViewById(R.id.btn_parsedata);
        toolbarPut = findViewById(R.id.toolbar_put);
        setSupportActionBar(toolbarPut);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnParseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putData(edtName.getText().toString(), edtJob.getText().toString());

            }
        });
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
