package com.material.smartelementary.activity.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.dashboard.MainDashboard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginCardOverlap extends AppCompatActivity {

    private View parent_view;
    private FirebaseAuth auth;
    private TextInputEditText inputEmail, inputPass;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);

        auth = FirebaseAuth.getInstance();
        parent_view = findViewById(android.R.id.content);

        inputEmail = findViewById(R.id.signInEmail);
        inputPass = findViewById(R.id.signInPass);
        signIn = findViewById(R.id.signInBtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                final String pass = inputPass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Please input your email");
                    return;
                }

                if (!isEmailValid(email)) {
                    inputEmail.setError("Please input your correct email");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    inputPass.setError("Input your password");
                }

                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(LoginCardOverlap.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    if (pass.length() < 8) {
                                        inputPass.setError("Password too short. Must be at least 8 characters");
                                    } else {
                                        Toast.makeText(LoginCardOverlap.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    startActivity(new Intent(LoginCardOverlap.this, MainDashboard.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        ((View) findViewById(R.id.sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginCardOverlap.this, RegisterCardLight.class);
                startActivity(register);
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
