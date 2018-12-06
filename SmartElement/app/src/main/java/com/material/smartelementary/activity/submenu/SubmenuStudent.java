package com.material.smartelementary.activity.submenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.dashboard.DashboardDatabase;

import java.util.HashMap;
import java.util.Map;

public class SubmenuStudent extends AppCompatActivity {

    private static final String TAG = "Student" ;
    private ScrollView stuLayout;
    private ProgressBar progressBar;
    private FrameLayout progressBarHolder;
    private LinearLayout npsnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu_student);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        DocumentReference docRefUser = db.collection("users").document(user.getUid());

        stuLayout = findViewById(R.id.stu_layout);
        npsnLayout = findViewById(R.id.npsn_layout);
        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        final TextInputEditText stu_npsn_check = findViewById(R.id.stu_check_npsn);
        final Button check_npsn = findViewById(R.id.submit_check);

        final TextInputEditText stu_name = findViewById(R.id.stu_name);
        final TextInputEditText stu_addr = findViewById(R.id.stu_addr);
        final TextInputEditText stu_nik = findViewById(R.id.stu_nik);
        final TextInputEditText stu_npsn = findViewById(R.id.stu_npsn);
        final TextInputEditText stu_rel = findViewById(R.id.stu_rel);
        final TextInputEditText stu_dob = findViewById(R.id.stu_dob);
        final TextInputEditText stu_gender = findViewById(R.id.stu_gen);
        final TextInputEditText stu_parent = findViewById(R.id.stu_parent);
        final TextInputEditText stu_nat = findViewById(R.id.stu_nat);
        final TextInputEditText stu_phone = findViewById(R.id.stu_phone);
        final TextInputEditText stu_blood = findViewById(R.id.stu_blood);
        final TextInputEditText stu_sch_name = findViewById(R.id.stu_sch);

        final TextInputEditText vac_dpt = findViewById(R.id.vac_dpt);
        final TextInputEditText vac_pol = findViewById(R.id.vac_polio);
        final TextInputEditText vac_mmr = findViewById(R.id.vac_mmr);
        final TextInputEditText vac_tif = findViewById(R.id.vac_tif);
        final TextInputEditText vac_inf = findViewById(R.id.vac_inf);
        final TextInputEditText vac_hpv = findViewById(R.id.vac_hpv);

        final Button sch_submit = findViewById(R.id.submit);

        check_npsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoadingActiveCheck();

                final String npsn_check = stu_npsn_check.getText().toString();

                DocumentReference schRef = db.collection("schools").document(npsn_check);

                if (TextUtils.isEmpty(npsn_check)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                schRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot check = task.getResult();
                            if (check.exists()) {
                                Toast.makeText(getApplicationContext(), "School has registered.", Toast.LENGTH_SHORT).show();
                                isLoadingInactiveCheck();
                                String sch_name_check = check.getString("name");
                                stu_npsn.setText(npsn_check);
                                stu_sch_name.setText(sch_name_check);
                                stu_npsn.setKeyListener(null);
                                stu_sch_name.setKeyListener(null);
                                return;
                            } else {
                                Toast.makeText(getApplicationContext(), "School not found. Check your NPSN again", Toast.LENGTH_SHORT).show();
                                isLoadingInactiveCheckFalse();
                                return;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Data error.", Toast.LENGTH_SHORT).show();
                            isLoadingInactiveCheckFalse();
                            return;
                        }
                    }
                });

            }
        });

        sch_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoadingActive();

                final String name = stu_name.getText().toString();
                final String addr = stu_addr.getText().toString();
                final String nik = stu_nik.getText().toString();
                final String npsn = stu_npsn.getText().toString();
                final String phone = stu_phone.getText().toString();
                final String rel = stu_rel.getText().toString();
                final String blood = stu_blood.getText().toString();
                final String sch_name = stu_sch_name.getText().toString();
                final String nat = stu_nat.getText().toString();
                final String dob = stu_dob.getText().toString();
                final String gen = stu_gender.getText().toString();
                final String parent = stu_parent.getText().toString();

                final String dpt = vac_dpt.getText().toString();
                final String hpv = vac_hpv.getText().toString();
                final String polio = vac_pol.getText().toString();
                final String mmr = vac_mmr.getText().toString();
                final String tif = vac_tif.getText().toString();
                final String inf = vac_inf.getText().toString();

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
                if (TextUtils.isEmpty(nik)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(rel)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(blood)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sch_name)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nat)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dob)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(parent)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(gen)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(hpv)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(polio)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inf)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mmr)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tif)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(dpt)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final DocumentReference docRefStu = db.collection("students").document(nik);
                final DocumentReference docRefVac = db.collection("vaccines").document("total");

                Map<String, Object> vaccine = new HashMap<>();
                vaccine.put("hpv", hpv);
                vaccine.put("polio", polio);
                vaccine.put("mmr", mmr);
                vaccine.put("influenza", inf);
                vaccine.put("dpt", dpt);
                vaccine.put("tifoid", tif);

                Map<String, Object> student = new HashMap<>();
                student.put("name", name);
                student.put("address", addr);
                student.put("nik", nik);
                student.put("npsn", npsn);
                student.put("gender", gen);
                student.put("nat", nat);
                student.put("religion", rel);
                student.put("school_name", sch_name);
                student.put("blood", blood);
                student.put("parent", parent);
                student.put("phone", phone);
                student.put("dob", dob);
                student.put("vaccines", vaccine);

                db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot data = transaction.get(docRefVac);
                        if (hpv.equals("sudah")){
                            int newHPV = data.getLong("hpv").intValue() + 1;
                            transaction.update(docRefVac, "hpv", newHPV);
                        } else if (hpv.equals("belum")) {
                            int newHPV = data.getLong("hpv").intValue();
                            transaction.update(docRefVac, "hpv", newHPV);
                        }
                        if (polio.equals("sudah")){
                            int newPolio = data.getLong("polio").intValue() + 1;
                            transaction.update(docRefVac, "polio", newPolio);
                        } else if (polio.equals("belum")) {
                            int newPolio = data.getLong("polio").intValue();
                            transaction.update(docRefVac, "polio", newPolio);
                        }
                        if (inf.equals("sudah")){
                            int newInf = data.getLong("influenza").intValue() + 1;
                            transaction.update(docRefVac, "influenza", newInf);
                        } else if (inf.equals("belum")) {
                            int newInf = data.getLong("influenza").intValue();
                            transaction.update(docRefVac, "influenza", newInf);
                        }
                        if (tif.equals("sudah")){
                            int newTif = data.getLong("tifoid").intValue() + 1;
                            transaction.update(docRefVac, "tifoid", newTif);
                        } else if (tif.equals("belum")) {
                            int newTif = data.getLong("tifoid").intValue();
                            transaction.update(docRefVac, "tifoid", newTif);
                        }
                        if (mmr.equals("sudah")){
                            int newMMR = data.getLong("mmr").intValue() + 1;
                            transaction.update(docRefVac, "mmr", newMMR);
                        } else if (mmr.equals("belum")) {
                            int newMMR = data.getLong("mmr").intValue();
                            transaction.update(docRefVac, "mmr", newMMR);
                        }
                        if (dpt.equals("sudah")){
                            int newDPT = data.getLong("dpt").intValue() + 1;
                            transaction.update(docRefVac, "dpt", newDPT);
                        } else if (dpt.equals("belum")) {
                            int newDPT = data.getLong("dpt").intValue();
                            transaction.update(docRefVac, "dpt", newDPT);
                        }
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction success!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });

                docRefStu
                        .set(student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SubmenuStudent.this, getString(R.string.data_success), Toast.LENGTH_SHORT).show();
                                isLoadingInactive();
                                startActivity(new Intent(SubmenuStudent.this, DashboardDatabase.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SubmenuStudent.this, getString(R.string.data_failed), Toast.LENGTH_SHORT).show();
                                isLoadingInactive();
                            }
                        });
            }
        });

    }

    private void isLoadingActiveCheck() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        npsnLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactiveCheck() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        stuLayout.setVisibility(View.VISIBLE);
        npsnLayout.setVisibility(View.GONE);
    }

    private void isLoadingInactiveCheckFalse() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        npsnLayout.setVisibility(View.VISIBLE);
    }

    private void isLoadingActive() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        stuLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactive() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        stuLayout.setVisibility(View.VISIBLE);
    }
}
