package com.schoolapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.schoolapp.R;

public class TimeTableActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {
    private static final String TAG = "Timetable Activity";
    public static final String TIME_TABLE_FILE = "timetable.pdf";
    Toolbar mTimeTableToolbar;
    TextView mToolbarTitle;

    PDFView mTimeTablePdfView;
    String mTimetablePdfFileName;
    Integer pageNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        initialiseViews();

        setupToolbar(getBundleValue());

        displayFromAsset(TIME_TABLE_FILE);
    }

    private void initialiseViews() {
        mTimeTableToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mTimeTablePdfView = (PDFView) findViewById(R.id.timeTablePdfView);
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
        if (mTimeTableToolbar != null) {
            setSupportActionBar(mTimeTableToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayFromAsset(String assetFileName) {
        mTimetablePdfFileName = assetFileName;
        mTimeTablePdfView.fromAsset(TIME_TABLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
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

    @Override
    public void loadComplete(int nbPages) {
        Log.d(TAG, "On Load complete = " + nbPages);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Log.d(TAG, "On Page changed = " + page + ", Page count = " + pageCount);
    }
}
