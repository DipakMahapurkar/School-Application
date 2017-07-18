package com.schoolapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.schoolapp.R;
import com.schoolapp.adapters.MonthlyReportAdapter;
import com.schoolapp.models.MonthlyReportModel;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReportActivity extends AppCompatActivity {

    private static final String TAG = "Monthly Report Activity";
    Toolbar mMonthlyReportToolbar;
    TextView mToolbarTitle;

    BarChart mMontlyChart;

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
//        mMontlyChart = (BarChart) findViewById(R.id.monthly_chart);

        mMonthReportRecyclerView = (RecyclerView) findViewById(R.id.monthly_report_recycler_view);

        mMonthReportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMonthReportRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String getBundleValue() {
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

    private void staticMonthReportData() {
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
       /* BarData data = new BarData(getXAxisValues(), getDataSet());
        mMontlyChart.setData(data);
        mMontlyChart.animateXY(2000, 2000);
        mMontlyChart.invalidate();*/
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
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
