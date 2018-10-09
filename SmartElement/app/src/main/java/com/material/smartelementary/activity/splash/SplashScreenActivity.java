package com.material.smartelementary.activity.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.auth.FirebaseAuth;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.login.LoginCardOverlap;

public class SplashScreenActivity extends AppCompatActivity {

    private SpinKitView loadSpin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth = FirebaseAuth.getInstance();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadSpin = (SpinKitView) findViewById(R.id.loadSpin);
                if (auth.getCurrentUser() != null) {
//                    startActivity(new Intent(LoginCardOverlap.class, ));
                } else {
                    Intent login = new Intent(SplashScreenActivity.this, LoginCardOverlap.class);
                    startActivity(login);
                    finish();
                }
            }
        }, 3000);
    }


}
