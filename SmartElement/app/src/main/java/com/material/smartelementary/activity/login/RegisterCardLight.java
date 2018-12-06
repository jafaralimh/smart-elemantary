package com.material.smartelementary.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.material.smartelementary.R;
import com.material.smartelementary.model.Users;
import com.material.smartelementary.utils.Tools;
import com.material.smartelementary.utils.ViewAnimation;

import java.util.HashMap;
import java.util.Map;

public class RegisterCardLight extends AppCompatActivity {

    private static final String TAG = "USER" ;
    private View parent_view;
    private ScrollView regLayout;
    private ProgressBar progressBar;
    private FrameLayout progressBarHolder;

    public static final String Db_Path = "users";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_card_light);
        parent_view = findViewById(android.R.id.content);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final TextInputEditText r_email = findViewById(R.id.reg_email);
        final TextInputEditText r_pass = findViewById(R.id.reg_pass);
        final TextInputEditText r_name = findViewById(R.id.reg_name);
        final TextInputEditText r_dob = findViewById(R.id.reg_dob);
        final TextInputEditText r_pos = findViewById(R.id.reg_pos);
        final TextInputEditText r_nip = findViewById(R.id.reg_nip);
        final TextInputEditText r_npsn = findViewById(R.id.reg_npsn);
        final TextInputEditText r_phone = findViewById(R.id.reg_phone);
        final TextInputEditText r_status = findViewById(R.id.reg_status);
        final TextInputEditText r_key = findViewById(R.id.reg_key);

        final Button submit = findViewById(R.id.submit);

        regLayout = findViewById(R.id.reg_layout);
        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = r_email.getText().toString().trim();
                String pass = r_pass.getText().toString().trim();
                final String name = r_name.getText().toString();
                final String pos = r_pos.getText().toString();
                final String dob = r_dob.getText().toString();
                final String nip = r_nip.getText().toString();
                final String npsn = r_npsn.getText().toString();
                final String phone = r_phone.getText().toString();
                final String status = r_status.getText().toString();
                final String key = r_key.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nip)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pos)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(status)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(npsn)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(key) && !TextUtils.equals(key,"Telematika")) {
                    Toast.makeText(getApplicationContext(), "Key failed!", Toast.LENGTH_SHORT).show();
                    return;
                }

                isLoadingActive();

                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(RegisterCardLight.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull
                                                   final Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(RegisterCardLight.this, "Authentication failed!" + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser getUserId = auth.getCurrentUser();

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("dob", dob);
                                    user.put("pos", pos);
                                    user.put("nip", nip);
                                    user.put("npsn", npsn);
                                    user.put("status", status);
                                    user.put("phone", phone);

                                    if (TextUtils.isEmpty(key)) {
//                                        userFire.setKeyUser("user");
                                        user.put("key", "user");
                                    } else {
//                                        userFire.setKeyUser("admin");
                                        user.put("key", "admin");
                                    }
                                    /*String userRecord = databaseRef.push().getKey();
                                    databaseRef.child(userRecord).setValue(userFire);*/
                                    db.collection(Db_Path)
                                            .document(getUserId.getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    isLoadingInactive();
                                                    startActivity(new Intent(RegisterCardLight.this, LoginCardOverlap.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }

    private void isLoadingActive() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        regLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactive() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        regLayout.setVisibility(View.VISIBLE);
    }
}
