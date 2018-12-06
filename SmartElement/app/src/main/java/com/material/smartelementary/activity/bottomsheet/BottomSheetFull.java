package com.material.smartelementary.activity.bottomsheet;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.material.smartelementary.R;
import com.material.smartelementary.adapter.AdapterPeopleLeft;
import com.material.smartelementary.data.DataGenerator;
import com.material.smartelementary.fragment.FragmentBottomSheetDialogFull;
import com.material.smartelementary.model.People;
import com.material.smartelementary.utils.Tools;

public class BottomSheetFull extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterPeopleLeft adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_full);
        }

}
