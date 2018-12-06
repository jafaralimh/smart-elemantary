package com.material.smartelementary.activity.dashboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.material.smartelementary.R;
import com.material.smartelementary.activity.bottomsheet.BottomSheetFull;
import com.material.smartelementary.activity.login.LoginCardOverlap;
import com.material.smartelementary.utils.Tools;

import org.w3c.dom.Text;

public class DashboardGridFab extends AppCompatActivity {

    private static final String TAG = "DASHBOARD";
    private ProgressBar progressBar;
    private FrameLayout progressBarHolder;
    private RelativeLayout homeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_grid_fab);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        DocumentReference docRef = db.collection("users").document(user.getUid());

        FloatingActionButton home_button = findViewById(R.id.home_dashboard);
        FloatingActionButton vaksin_button = findViewById(R.id.vaksin_dashboard);
        final FloatingActionButton database_button = findViewById(R.id.database_dashboard);
        FloatingActionButton panduan_button = findViewById(R.id.panduan_dashboard);
        FloatingActionButton logout = findViewById(R.id.logout_home);
        final TextView username = findViewById(R.id.text_name);

        homeLayout = findViewById(R.id.home_layout);
        progressBar = findViewById(R.id.progressBar);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        isLoadingActive();

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot data = task.getResult();
                    if (data.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + data.getData());
                        String name = data.getString("name");
                        final String key = data.getString("key");
                        username.setText(name);
                        database_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (key.equals("admin")) {
                                    startActivity(new Intent(DashboardGridFab.this, DashboardDatabase.class));
                                } else if (key.equals("user")) {
                                    startActivity(new Intent(DashboardGridFab.this, DashboardFlight.class));
                                }
                            }
                        });
                        isLoadingInactive();
                    } else {
                        Log.d(TAG, "No such document");
                        isLoadingInactive();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    isLoadingInactive();
                }
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DashboardGridFab.this, DashboardStatistics.class);
                startActivity(new Intent(DashboardGridFab.this, DashboardStatistics.class));
            }
        });
        database_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent2 = new Intent(DashboardGridFab.this, DashboardFlight.class);
//                startActivity(new Intent(DashboardGridFab.this, DashboardFlight.class));
                startActivity(new Intent(DashboardGridFab.this, DashboardDatabase.class));
            }
        });

        vaksin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent3 = new Intent(DashboardGridFab.this, DashboardPayBill.class);
                startActivity(new Intent(DashboardGridFab.this, DashboardPayBill.class));
            }
        });

        panduan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardGridFab.this, BottomSheetFull.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(DashboardGridFab.this, LoginCardOverlap.class));
                finish();
            }
        });

    }

    private void isLoadingActive() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHolder.setVisibility(View.VISIBLE);
        homeLayout.setVisibility(View.INVISIBLE);
    }

    private void isLoadingInactive() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBar.setVisibility(View.GONE);
        progressBarHolder.setVisibility(View.GONE);
        homeLayout.setVisibility(View.VISIBLE);
    }

}
