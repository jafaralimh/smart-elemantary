package com.material.smartelementary.activity.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.login.LoginCardOverlap;
import com.material.smartelementary.utils.Tools;

public class DashboardStatistics extends AppCompatActivity {

    private TextView sch_name_info;
    private TextView sch_addr_info;
    private TextView sch_status_info;
    private TextView sch_phone_info;
    private TextView sch_npsn_info;

    private TextView vac_hpv_info;
    private TextView vac_pol_info;
    private TextView vac_inf_info;
    private TextView vac_mmr_info;
    private TextView vac_tif_info;
    private TextView vac_dpt_info;

    private NestedScrollView homeLay;
    private FrameLayout progressBarHolder;
    private ProgressBar progressBar;

    private FirebaseFirestore db;
    private DocumentReference docRefUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_statistics);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        docRefUser = db.collection("users").document(user.getUid());

        homeLay = findViewById(R.id.nested_scroll_view_home);

        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        sch_name_info = findViewById(R.id.info_sch_name_res);
        sch_addr_info = findViewById(R.id.info_sch_addr_res);
        sch_status_info = findViewById(R.id.info_sch_status_res);
        sch_phone_info = findViewById(R.id.info_sch_phone_res);
        sch_npsn_info = findViewById(R.id.info_sch_npsn_res);

        vac_hpv_info = findViewById(R.id.vac_hpv_dash);
        vac_pol_info = findViewById(R.id.vac_pol_dash);
        vac_inf_info = findViewById(R.id.vac_inf_dash);
        vac_mmr_info = findViewById(R.id.vac_mmr_dash);
        vac_tif_info = findViewById(R.id.vac_tif_dash);
        vac_dpt_info = findViewById(R.id.vac_dpt_dash);

        fetchData();

    }

    private void fetchData() {
        docRefUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    isLoadingActive();
                    DocumentSnapshot data = task.getResult();
                    if (data.exists()) {
                        String npsn_res = data.getString("npsn");

                        DocumentReference docRefSchool = db.collection("schools").document(npsn_res);

                        DocumentReference docRefVac = db.collection("vaccines").document("total");

                        docRefSchool.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                                if (task2.isSuccessful()) {
                                    DocumentSnapshot data_sch = task2.getResult();
                                    if (data_sch.exists()) {
                                        sch_name_info.setText(data_sch.getString("name"));
                                        sch_addr_info.setText(data_sch.getString("address"));
                                        sch_status_info.setText(data_sch.getString("status"));
                                        sch_phone_info.setText(data_sch.getString("phone"));
                                        sch_npsn_info.setText(data_sch.getString("npsn"));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Data has been removed.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Data error.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        docRefVac.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task3) {
                                if (task3.isSuccessful()) {
                                    DocumentSnapshot data_vac = task3.getResult();
                                    if (data_vac.exists()) {
                                        int conv_hpv = 0;
                                        int conv_pol = 0;
                                        int conv_mmr = 0;
                                        int conv_inf = 0;
                                        int conv_tif = 0;
                                        int conv_dpt = 0;

                                        conv_hpv = data_vac.getLong("hpv").intValue();
                                        conv_pol = data_vac.getLong("polio").intValue();
                                        conv_mmr = data_vac.getLong("mmr").intValue();
                                        conv_inf = data_vac.getLong("influenza").intValue();
                                        conv_tif = data_vac.getLong("tifoid").intValue();
                                        conv_dpt = data_vac.getLong("dpt").intValue();

                                        vac_hpv_info.setText(Integer.toString(conv_hpv));
                                        vac_pol_info.setText(Integer.toString(conv_pol));
                                        vac_mmr_info.setText(Integer.toString(conv_mmr));
                                        vac_inf_info.setText(Integer.toString(conv_inf));
                                        vac_tif_info.setText(Integer.toString(conv_tif));
                                        vac_dpt_info.setText(Integer.toString(conv_dpt));
                                        isLoadingInactive();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Data has been removed.", Toast.LENGTH_SHORT).show();
                                        isLoadingInactive();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Data error.", Toast.LENGTH_SHORT).show();
                                    isLoadingInactive();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Your data is illegal.", Toast.LENGTH_SHORT).show();
                        isLoadingInactive();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Data error.", Toast.LENGTH_SHORT).show();
                    isLoadingInactive();
                }
            }
        });
    }

    private void isLoadingActive() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        homeLay.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactive() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        homeLay.setVisibility(View.VISIBLE);
    }

}
