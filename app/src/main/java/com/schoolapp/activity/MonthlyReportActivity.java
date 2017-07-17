package com.schoolapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.adapters.MonthlyReportAdapter;
import com.schoolapp.models.MonthlyReportModel;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReportActivity extends AppCompatActivity {

    private static final String TAG = "Monthly Report Activity";
    Toolbar mMonthlyReportToolbar;
    TextView mToolbarTitle;

    RecyclerView mMonthReportRecyclerView;
    List<MonthlyReportModel> mMonthlyReportModelList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report);

        initialiseViews();

        setupToolbar(getBundleValue());

        staticMonthReportData();
    }

    private void initialiseViews() {
        mMonthlyReportToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mMonthReportRecyclerView = (RecyclerView) findViewById(R.id.monthly_report_recycler_view);

        mMonthReportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMonthReportRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String getBundleValue(){
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("CAT_NAME");
        }
        Log.d(TAG, value);
        return value;
    }


    private void setupToolbar(String toolbarTitle) {
        if (mMonthlyReportToolbar != null) {
            setSupportActionBar(mMonthlyReportToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void staticMonthReportData(){
        mMonthlyReportModelList = new ArrayList<>();

        mMonthlyReportModelList.add(new MonthlyReportModel(1, "January 2017", "A+"));
        mMonthlyReportModelList.add(new MonthlyReportModel(2, "February 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(3, "March 2017", "A+"));
        mMonthlyReportModelList.add(new MonthlyReportModel(4, "April 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(5, "May 2017", "A+"));
        mMonthlyReportModelList.add(new MonthlyReportModel(6, "Jun 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(7, "July 2017", "A+"));
        mMonthlyReportModelList.add(new MonthlyReportModel(8, "August 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(9, "September 2017", "A+"));
        mMonthlyReportModelList.add(new MonthlyReportModel(10, "October 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(11, "November 2017", "A"));
        mMonthlyReportModelList.add(new MonthlyReportModel(12, "December 2017", "A+"));

        mMonthReportRecyclerView.setAdapter(new MonthlyReportAdapter(this, mMonthlyReportModelList));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
