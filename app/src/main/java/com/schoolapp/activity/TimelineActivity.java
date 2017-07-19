package com.schoolapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.adapters.TimelineListAdapter;
import com.schoolapp.apiModels.TimelineGetAPIResponseModel;
import com.schoolapp.models.TimelineListModel;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.Constant;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schoolapp.utils.Constant.SUCCESS_STATUS_CODE;

public class TimelineActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Timeline Activity";
    Toolbar mTimelineToolbar;
    TextView mToolbarTitle;
    RecyclerView mTimelineRecyclerView;
    List<TimelineListModel> mTimelineList;
    View mResultNotFoundLayout;

    FloatingActionButton mAddTimelineFab;

    APICallInterface mApiCallInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar(getBundleValue());

        staticTimelineData();
    }

    private void initialiseViews() {
        mTimelineToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mTimelineRecyclerView = (RecyclerView) findViewById(R.id.timeline_recycler_view);
        mAddTimelineFab = (FloatingActionButton) findViewById(R.id.idAddTimelineFab);
        mResultNotFoundLayout = findViewById(R.id.layout_no_result);
        mResultNotFoundLayout.setVisibility(View.GONE);
        mTimelineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTimelineRecyclerView.setHasFixedSize(true);

        if (Constant.USER_ROLE.equals("Teacher")) {
            mAddTimelineFab.setVisibility(View.VISIBLE);
        }

        mTimelineRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Constant.USER_ROLE.equals("Teacher")) {
                    if (dy > 0)
                        mAddTimelineFab.hide();
                    else if (dy < 0)
                        mAddTimelineFab.show();
                }
            }
        });

        mAddTimelineFab.setOnClickListener(this);
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
        if (mTimelineToolbar != null) {
            setSupportActionBar(mTimelineToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void staticTimelineData() {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        mApiCallInterface.getTimelineAPI().enqueue(new Callback<TimelineGetAPIResponseModel>() {
            @Override
            public void onResponse(Call<TimelineGetAPIResponseModel> call, Response<TimelineGetAPIResponseModel> response) {
                mTimelineList = new ArrayList<>();
                Log.d(TAG, "Response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE) {
                    if (response.body().getData() != null && response.body().getData().size() >= 0) {
                        mResultNotFoundLayout.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mTimelineList.add(new TimelineListModel(response.body().getData().get(i).getId(),
                                    response.body().getData().get(i).getTimelineText(),
                                    response.body().getData().get(i).getTeacherId(),
                                    response.body().getData().get(i).getTimelineImage(),
                                    response.body().getData().get(i).getCreatedAt(),
                                    response.body().getData().get(i).getTeacherName()));
                        }
                        mTimelineRecyclerView.setAdapter(new TimelineListAdapter(TimelineActivity.this, mTimelineList));
                    } else {
                        mResultNotFoundLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Utils.showToast(TimelineActivity.this, "Some thing get wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<TimelineGetAPIResponseModel> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
                ProgressDialogUtils.dismiss();
            }
        });

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idAddTimelineFab:
                startActivityForResult(new Intent(this, AddTimelineActivity.class), 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                staticTimelineData();
            }
        }
    }
}
