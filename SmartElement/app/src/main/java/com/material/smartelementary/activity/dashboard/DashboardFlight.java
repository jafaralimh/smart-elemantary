package com.material.smartelementary.activity.dashboard;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.material.smartelementary.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardFlight extends AppCompatActivity {

    private ProgressBar progressBar;
    private FrameLayout progressBarHolder;
    private LinearLayout card1Layout, card2Layout, card3Layout, searchLayout;
    private CardView dataInfoLayout;
    private NestedScrollView nikLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_flight);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        DocumentReference docRefUser = db.collection("users").document(user.getUid());

        searchLayout = findViewById(R.id.search_block);
        nikLayout = findViewById(R.id.nik_search_layout);
        dataInfoLayout = findViewById(R.id.datasiswa);
        card1Layout = findViewById(R.id.vaccines1_res);
        card2Layout = findViewById(R.id.vaccines2_res);
        card3Layout = findViewById(R.id.vaccines3_res);
        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        final EditText search_text = findViewById(R.id.nik_search);
        final ImageButton search_btn = findViewById(R.id.search_stu);

        final TextView name = findViewById(R.id.name_res);
        final TextView nik = findViewById(R.id.nik_res);
        final TextView blood = findViewById(R.id.blood_res);
        final TextView dob = findViewById(R.id.dob_res);
        final TextView sch_name = findViewById(R.id.sch_name_res);
        final TextView rel = findViewById(R.id.rel_res);
        final TextView gen = findViewById(R.id.gen_res);
        final TextView parent = findViewById(R.id.parent_res);
        final TextView addr = findViewById(R.id.addr_res);
        final TextView nat = findViewById(R.id.nat_res);
        final TextView phone = findViewById(R.id.phone_res);
        final TextView npsn = findViewById(R.id.npsn_res);

        final TextView hpv = findViewById(R.id.hpv_kosong);
        final TextView dpt = findViewById(R.id.dpt_kosong);
        final TextView mmr = findViewById(R.id.mmr_kosong);
        final TextView pol = findViewById(R.id.polio_kosong);
        final TextView inf = findViewById(R.id.inf_kosong);
        final TextView tif = findViewById(R.id.tif_kosong);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoadingActiveCheck();

                final String search_res = search_text.getText().toString();

                DocumentReference stuRef = db.collection("students").document(search_res);

                if (TextUtils.isEmpty(search_res)) {
                    Toast.makeText(getApplicationContext(), "Fill the blank one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                stuRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot check = task.getResult();
                            if (check.exists()) {
                                Toast.makeText(getApplicationContext(), "Data has registered.", Toast.LENGTH_SHORT).show();

                                String name_res = check.getString("name");
                                String addr_res = check.getString("address");
                                String nat_res = check.getString("nat");
                                String gen_res = check.getString("gender");
                                String blood_res = check.getString("blood");
                                String npsn_res = check.getString("npsn");
                                String parent_res = check.getString("parent");
                                String rel_res = check.getString("religion");
                                String dob_res = check.getString("dob");
                                String phone_res = check.getString("phone");
                                String sch_name_res = check.getString("school_name");

                                HashMap<String, String> vaccines = (HashMap<String, String>) check.getData().get("vaccines");

                                String vac_hpv_res = vaccines.get("hpv");
                                String vac_dpt_res = vaccines.get("dpt");
                                String vac_inf_res = vaccines.get("influenza");
                                String vac_tif_res = vaccines.get("tifoid");
                                String vac_mmr_res = vaccines.get("mmr");
                                String vac_pol_res = vaccines.get("polio");

                                if (vac_hpv_res.equals("sudah")) {
                                    vac_hpv_res = "Sudah";
                                } else if (vac_hpv_res.equals("belum")) {
                                    vac_hpv_res = "Belum";
                                }
                                if (vac_dpt_res.equals("sudah")) {
                                    vac_dpt_res = "Sudah";
                                } else if (vac_dpt_res.equals("belum")) {
                                    vac_dpt_res = "Belum";
                                }
                                if (vac_inf_res.equals("sudah")) {
                                    vac_inf_res = "Sudah";
                                } else if (vac_inf_res.equals("belum")) {
                                    vac_inf_res = "Belum";
                                }
                                if (vac_tif_res.equals("sudah")) {
                                    vac_tif_res = "Sudah";
                                } else if (vac_tif_res.equals("belum")) {
                                    vac_tif_res = "Belum";
                                }
                                if (vac_mmr_res.equals("sudah")) {
                                    vac_mmr_res = "Sudah";
                                } else if (vac_mmr_res.equals("belum")) {
                                    vac_mmr_res = "Belum";
                                }
                                if (vac_pol_res.equals("sudah")) {
                                    vac_pol_res = "Sudah";
                                } else if (vac_pol_res.equals("belum")) {
                                    vac_pol_res = "Belum";
                                }

                                nik.setText(search_res);
                                name.setText(name_res);
                                addr.setText(addr_res);
                                npsn.setText(npsn_res);
                                sch_name.setText(sch_name_res);
                                rel.setText(rel_res);
                                blood.setText(blood_res);
                                dob.setText(dob_res);
                                gen.setText(gen_res);
                                parent.setText(parent_res);
                                nat.setText(nat_res);
                                phone.setText(phone_res);
                                hpv.setText(vac_hpv_res);
                                dpt.setText(vac_dpt_res);
                                inf.setText(vac_inf_res);
                                tif.setText(vac_tif_res);
                                mmr.setText(vac_mmr_res);
                                pol.setText(vac_pol_res);

                                isLoadingInactiveCheck();
                            } else {
                                Toast.makeText(getApplicationContext(), "School not found. Check your NIK again", Toast.LENGTH_SHORT).show();
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

    }

    private void isLoadingActiveCheck() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        nikLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactiveCheck() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        nikLayout.setVisibility(View.VISIBLE);
        dataInfoLayout.setVisibility(View.VISIBLE);
        card1Layout.setVisibility(View.VISIBLE);
        card2Layout.setVisibility(View.VISIBLE);
        card3Layout.setVisibility(View.VISIBLE);
    }

    private void isLoadingInactiveCheckFalse() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        searchLayout.setVisibility(View.VISIBLE);
        nikLayout.setVisibility(View.VISIBLE);
        dataInfoLayout.setVisibility(View.GONE);
        card1Layout.setVisibility(View.GONE);
        card2Layout.setVisibility(View.GONE);
        card3Layout.setVisibility(View.GONE);
    }
}
