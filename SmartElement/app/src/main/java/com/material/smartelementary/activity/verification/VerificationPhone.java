package com.material.smartelementary.activity.verification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.material.smartelementary.R;
import com.material.smartelementary.utils.Tools;

public class VerificationPhone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_phone);
        Tools.setSystemBarColor(this, R.color.grey_20);
    }
}
