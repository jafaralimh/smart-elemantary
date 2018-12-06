package com.material.smartelementary.activity.submenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.dashboard.DashboardDatabase;

import java.util.HashMap;
import java.util.Map;

public class SubmenuSchool extends AppCompatActivity {

    private ScrollView schLayout;
    private ProgressBar progressBar;
    private FrameLayout progressBarHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_school);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        DocumentReference docRefUser = db.collection("users").document(user.getUid());

        schLayout = findViewById(R.id.sch_layout);
        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        final TextInputEditText sch_name = findViewById(R.id.sch_name);
        final TextInputEditText sch_addr = findViewById(R.id.sch_addr);
        final TextInputEditText sch_npsn = findViewById(R.id.sch_npsn);
        final TextInputEditText sch_phone = findViewById(R.id.sch_phone);
        final TextInputEditText sch_status = findViewById(R.id.sch_status);
        final Button sch_submit = findViewById(R.id.submit);

        sch_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoadingActive();

                final String name = sch_name.getText().toString();
                final String addr = sch_addr.getText().toString();
                final String npsn = sch_npsn.getText().toString();
                final String phone = sch_phone.getText().toString();
                final String status = sch_status.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(addr)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
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

                final DocumentReference docRefSch = db.collection("schools").document(npsn);

                Map<String, Object> school = new HashMap<>();
                school.put("name", name);
                school.put("address", addr);
                school.put("npsn", npsn);
                school.put("status", status);
                school.put("phone", phone);

                docRefSch
                        .set(school)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SubmenuSchool.this, getString(R.string.data_success), Toast.LENGTH_SHORT).show();
                                isLoadingInactive();
                                startActivity(new Intent(SubmenuSchool.this, DashboardDatabase.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SubmenuSchool.this, getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
                                isLoadingInactive();
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
        schLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactive() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        schLayout.setVisibility(View.VISIBLE);
    }
}
