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

public class PostActivity extends AppCompatActivity {

    TextView tvName, tvDomain;
    EditText edtName, edtDomain;
    Button btnPost;
    Toolbar toolbarPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        init();
    }

    public void postMethod(final String name, final String domain) {
        RequestQueue queue = Volley.newRequestQueue(this);// this = context
        String url = "https://reqres.in/api/users";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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
                        Log.e("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("domain", domain);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void parseData(String response) {
        try {
            JSONObject josnData = new JSONObject(response);

            String name = josnData.getString("name");
            String domain = josnData.getString("domain");

            tvName.setText("name: " + name);
            tvDomain.setText("domain: " + domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        tvName = findViewById(R.id.tv_name);
        tvDomain = findViewById(R.id.tv_domain);
        edtName = findViewById(R.id.edt_name);
        edtDomain = findViewById(R.id.edt_domain);
        btnPost = findViewById(R.id.btn_postdata);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMethod(edtName.getText().toString(), edtDomain.getText().toString());
            }
        });
        toolbarPost = findViewById(R.id.toolbar_post);
        setSupportActionBar(toolbarPost);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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