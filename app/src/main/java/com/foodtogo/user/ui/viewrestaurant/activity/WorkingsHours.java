package com.foodtogo.user.ui.viewrestaurant.activity;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodtogo.user.R;
import com.foodtogo.user.base.BaseActivity;
import com.foodtogo.user.model.restaurant.WorkingHour;
import com.foodtogo.user.ui.viewrestaurant.adapter.WorkingHoursAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class WorkingsHours extends BaseActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @BindView(R.id.tv_error)
    TextView tvError;

    ArrayList<WorkingHour> workingHourArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workingHourArrayList = getIntent().getParcelableArrayListExtra(WORKINGS_HOURS);
        setupToolBar();
        tvTitle.setText(getResources().getString(R.string.working_hrs));
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        WorkingHoursAdapter adapter = new WorkingHoursAdapter(workingHourArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_working_hours;
    }


}