package com.material.components.activity.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.material.components.R;

public class LoginCardOverlap extends AppCompatActivity {

    private View parent_view;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);

        auth = FirebaseAuth.getInstance();
        parent_view = findViewById(android.R.id.content);

        ((View) findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginCardOverlap.this, RegisterCardLight.class);
                startActivity(register);
            }
        });
    }
}
