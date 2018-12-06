package com.material.smartelementary.activity.dashboard;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.material.smartelementary.R;
import com.material.smartelementary.activity.submenu.SubmenuSchool;
import com.material.smartelementary.activity.submenu.SubmenuStudent;

public class DashboardDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_database);

        FloatingActionButton add_student = findViewById(R.id.add_student);
        FloatingActionButton add_school = findViewById(R.id.add_school);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardDatabase.this, SubmenuStudent.class));
            }
        });

        add_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardDatabase.this, SubmenuSchool.class));
            }
        });
    }
}
