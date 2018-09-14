package com.material.components.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.ybq.android.spinkit.SpinKitView;
import com.material.components.R;
import com.material.components.activity.login.LoginCardOverlap;

public class SplashScreenActivity extends AppCompatActivity {

    private SpinKitView loadSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadSpin = (SpinKitView) findViewById(R.id.loadSpin);
                Intent login = new Intent(SplashScreenActivity.this, LoginCardOverlap.class);
                startActivity(login);
                finish();
            }
        }, 3000);
    }


}
