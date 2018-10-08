package com.softices.trainingapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.softices.trainingapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ProgressBar processBar;
    Handler handler;
    Timer timer;
    Runnable runnable;
    RelativeLayout imageSplash;
    RelativeLayout layout_logo;
    Animation left_to_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        holdActivity();
        init();
        processBar.setVisibility(View.VISIBLE);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                processBar.setVisibility(View.GONE);
                timer.cancel();
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 50000, 10000);
    }

    public void holdActivity() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email", "");
        final Boolean isLogin = sharedPreferences.getBoolean("is_login", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, 2500);
    }

    public void init() {
        processBar = findViewById(R.id.prob_splash);
        layout_logo= findViewById(R.id.layout_logo);
        imageSplash= findViewById(R.id.image_splash);
        left_to_right = AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        imageSplash.setAnimation(left_to_right);
    }

}