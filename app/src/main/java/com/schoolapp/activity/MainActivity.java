package com.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.adapters.DashboardCatListAdapter;
import com.schoolapp.models.DashboardCatListModel;
import com.schoolapp.utils.Constant;
import com.schoolapp.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    Toolbar mToolbar;
    TextView mToolbarTitle, mUserNameTxt, mParentNameTxt, mClassNameTxt;
    ImageView mUserProfileImage;

    int[] sampleImages = {R.drawable.attendance_image, R.drawable.report, R.drawable.marksheet, R.drawable.timeline, R.drawable.homework_image, R.drawable.reminder, R.drawable.schedule, R.drawable.notice, R.drawable.application, R.drawable.about_image};
    String[] catNames = {"Attendance", "Monthly Report", "Marksheet", "Timeline", "Homework", "Fee Remainder", "Yearly Schedule", "Timetable", "Notice", "Applications"};

    RecyclerView mCategoryGridView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    List<DashboardCatListModel> mCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseViews();

        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(this, 2);
        mCategoryGridView.setLayoutManager(recyclerViewLayoutManager);

        setupCatGridView();

        setupToolbar(getString(R.string.str_home_fragment));

        getBundleValue();
    }

    private void initialiseViews() {
        mToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mUserNameTxt = (TextView) findViewById(R.id.user_name_txt);
        mParentNameTxt = (TextView) findViewById(R.id.parent_name_txt);
        mClassNameTxt = (TextView) findViewById(R.id.class_name_txt);
        mUserProfileImage = (ImageView) findViewById(R.id.logged_user_profile_img);
        mCategoryGridView = (RecyclerView) findViewById(R.id.idCategoryGridView);
    }

    private void setupCatGridView() {
        mCategoryList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            mCategoryList.add(new DashboardCatListModel(i, sampleImages[i], catNames[i]));
        }
        mCategoryGridView.setAdapter(new DashboardCatListAdapter(this, mCategoryList));
    }

    private void getBundleValue() {
        Constant.USER_ROLE = SharedPreference.getString(this, "USER_ROLE");
        mUserNameTxt.setText(SharedPreference.getString(this, "NAME"));
        if (Constant.USER_ROLE.equals("Teacher")) {
            mUserProfileImage.setImageResource(R.drawable.ic_teacher);
            mParentNameTxt.setText(SharedPreference.getString(this, "QUALIFICATION"));
            mClassNameTxt.setVisibility(View.GONE);
        }
        if(Constant.USER_ROLE.equals("Parent")) {
            mUserProfileImage.setImageResource(R.drawable.ic_family);
            mParentNameTxt.setText(SharedPreference.getString(this, "PARENT_NAME"));
            mClassNameTxt.setText("Class: " + SharedPreference.getString(this, "CLASS"));
        }
    }

    private void setupToolbar(String toolbarTitle) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreference.clearAll(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
