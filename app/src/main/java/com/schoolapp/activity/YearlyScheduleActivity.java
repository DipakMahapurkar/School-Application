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

public class YearlyScheduleActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    private static final String TAG = "Yearly Schedule Activity";
    public static final String YEARLY_SCHEDULE_FILE = "yearly_schedule.pdf";

    Toolbar mYearlyScheduleToolbar;
    TextView mToolbarTitle;

    PDFView mYearlySchedulePdfView;
    String mYearlySchedulePdfFileName;
    Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_schedule);

        initialiseViews();

        setupToolbar(getBundleValue());
        displayFromAsset(YEARLY_SCHEDULE_FILE);
    }

    private void initialiseViews() {
        mYearlyScheduleToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mYearlySchedulePdfView = (PDFView) findViewById(R.id.yearly_schedule_pdf_view);
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
        if (mYearlyScheduleToolbar != null) {
            setSupportActionBar(mYearlyScheduleToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayFromAsset(String assetFileName) {
        mYearlySchedulePdfFileName = assetFileName;
        mYearlySchedulePdfView.fromAsset(YEARLY_SCHEDULE_FILE)
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
    public void onPageChanged(int page, int pageCount) {
        Log.d(TAG, "On Page changed = " + page + ", Page count = " + pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {
        Log.d(TAG, "On Load complete = " + nbPages);
    }
}
