package com.schoolapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.fragment.ExamsFragment;
import com.schoolapp.fragment.MarksheetFragment;

public class MarksheetActivity extends AppCompatActivity {
    private static final String TAG = "Marksheet Activity";
    Toolbar mMarksheetToolbar;
    TextView mToolbarTitle;

    boolean flag = false;

    // Fragment manager
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marksheet);

        mFragmentManager = getSupportFragmentManager();

        initialiseViews();

        showExamFragment();
    }

    private void initialiseViews() {
        mMarksheetToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
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
        if (mMarksheetToolbar != null) {
            setSupportActionBar(mMarksheetToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showExamFragment() {
        setupToolbar("Select Exam");
        Fragment fragment = ExamsFragment.newInstance();
        if (mFragmentManager != null) {
            mFragmentManager.beginTransaction().replace(R.id.marksheet_container, fragment).commit();
        }
        flag = false;
    }

    public void showMarksheetFragment(int examId, String examName) {
        flag = true;
        setupToolbar("Marksheet");
        Fragment fragment = MarksheetFragment.newInstance();
        if (mFragmentManager != null) {
            Bundle args = new Bundle();
            args.putInt("MARK_SHEET_EXAM_ID", examId);
            args.putString("MARK_SHEET_EXAM_NAME", examName);
            fragment.setArguments(args);

            mFragmentManager.beginTransaction().replace(R.id.marksheet_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (flag){
                    showExamFragment();
                }else{
                    supportFinishAfterTransition();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
