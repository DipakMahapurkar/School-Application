package com.schoolapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.schoolapp.R;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = "Attendance Activity";
    Toolbar mAttendanceToolbar;
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        initialiseViews();

        setupToolbar(getBundleValue());
    }

    private void initialiseViews() {
        mAttendanceToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
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
        if (mAttendanceToolbar != null) {
            setSupportActionBar(mAttendanceToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
