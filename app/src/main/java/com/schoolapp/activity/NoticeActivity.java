package com.schoolapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.schoolapp.R;
import com.schoolapp.adapters.NotificationAdapter;
import com.schoolapp.apiModels.NoticeGetAPIResponseModel;
import com.schoolapp.models.NoticeModel;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.Constant;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.SharedPreference;
import com.schoolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schoolapp.utils.Constant.SUCCESS_STATUS_CODE;
import static com.schoolapp.utils.Utils.changeDateFormats;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Notice Activity";
    Toolbar mNoticeToolbar;
    TextView mToolbarTitle;
    View mResultNotFoundLayout;
    RecyclerView mNoticeRecyclerView;

    List<NoticeModel> mNoticeList;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton mFabStudent, mFabClass, mFabAllStudent;

    APICallInterface mApiCallInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar(getBundleValue());

        setUpStaticData();

        if (Constant.USER_ROLE.equals("Teacher")) {
            materialDesignFAM.setVisibility(View.VISIBLE);
        }

        mNoticeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Constant.USER_ROLE.equals("Teacher")) {
                    if (dy > 0)
                        materialDesignFAM.hideMenuButton(true);
                    else if (dy < 0)
                        materialDesignFAM.showMenuButton(true);
                }
            }
        });
    }

    private void initialiseViews() {
        mNoticeToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mNoticeRecyclerView = (RecyclerView) findViewById(R.id.notice_recycler_view);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        mFabStudent = (FloatingActionButton) findViewById(R.id.fab_student);
        mFabClass = (FloatingActionButton) findViewById(R.id.fab_class);
        mFabAllStudent = (FloatingActionButton) findViewById(R.id.fab_all_students);
        mResultNotFoundLayout = findViewById(R.id.layout_no_result);
        mResultNotFoundLayout.setVisibility(View.GONE);

        mNoticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNoticeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        materialDesignFAM.setClosedOnTouchOutside(true);
        mFabStudent.setOnClickListener(this);
        mFabClass.setOnClickListener(this);
        mFabAllStudent.setOnClickListener(this);
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
        if (mNoticeToolbar != null) {
            setSupportActionBar(mNoticeToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpStaticData() {
        /*mNoticeList.add(new NoticeModel(1, "Homework", "Solve the problem of 3rd unit", "1-May-2017"));
        mNoticeList.add(new NoticeModel(2, "Independence Day", "All Student should come in uniform", "15-Aug-2017"));
        mNoticeList.add(new NoticeModel(3, "Sport Week", "Sport week start from tomorrow", "16-Aug-2017"));
        mNoticeList.add(new NoticeModel(4, "Hockey", "1st game is Hockey", "17-Aug-2017"));
        mNoticeList.add(new NoticeModel(5, "Homework", "Solve the problem of 3rd unit", "1-May-2017"));
        mNoticeList.add(new NoticeModel(6, "Independence Day", "All Student should come in uniform", "15-Aug-2017"));
        mNoticeList.add(new NoticeModel(7, "Sport Week", "Sport week start from tomorrow", "16-Aug-2017"));
        mNoticeList.add(new NoticeModel(8, "Hockey", "1st game is Hockey", "17-Aug-2017"));
        mNoticeList.add(new NoticeModel(9, "Homework", "Solve the problem of 3rd unit", "1-May-2017"));
        mNoticeList.add(new NoticeModel(10, "Independence Day", "All Student should come in uniform", "15-Aug-2017"));
        mNoticeList.add(new NoticeModel(11, "Sport Week", "Sport week start from tomorrow", "16-Aug-2017"));
        mNoticeList.add(new NoticeModel(12, "Hockey", "1st game is Hockey", "17-Aug-2017"));*/
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        mApiCallInterface.noticeGetAPI(SharedPreference.getString(this, "ID")).enqueue(new Callback<NoticeGetAPIResponseModel>() {
            @Override
            public void onResponse(Call<NoticeGetAPIResponseModel> call, Response<NoticeGetAPIResponseModel> response) {
                Log.d(TAG, "Response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE) {
                    mNoticeList = new ArrayList<>();
                    if (response.body().getData() != null && response.body().getData().size() >= 0) {
                        mResultNotFoundLayout.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mNoticeList.add(new NoticeModel(response.body().getData().get(i).getId(),
                                    "Common Notice", response.body().getData().get(i).getNoticeDescription(),
                                    response.body().getData().get(i).getNoticeType(),
                                    response.body().getData().get(i).getClassId(),
                                    response.body().getData().get(i).getDivisionId(),
                                    response.body().getData().get(i).getStudentId(),
                                    response.body().getData().get(i).getCreatedAt()));
                        }
                        mNoticeRecyclerView.setAdapter(new NotificationAdapter(NoticeActivity.this, mNoticeList));
                    } else {
                        mResultNotFoundLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Utils.showToast(NoticeActivity.this, "Some thing get wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<NoticeGetAPIResponseModel> call, Throwable t) {
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
            case R.id.fab_student:
                materialDesignFAM.close(true);
                Intent intentA = new Intent(this, AddNoticeActivity.class);
                intentA.putExtra("CAT_ID", 1);
                intentA.putExtra("CAT_NAME", "TO_STUDENT");
                startActivityForResult(intentA, 1);
//                Utils.startActivityAfterCleanup(this, AddNoticeActivity.class, 1, "TO_STUDENT");
                break;

            case R.id.fab_class:
                materialDesignFAM.close(true);
                Intent intentB = new Intent(this, AddNoticeActivity.class);
                intentB.putExtra("CAT_ID", 2);
                intentB.putExtra("CAT_NAME", "TO_CLASS");
                startActivityForResult(intentB, 1);
//                Utils.startActivityAfterCleanup(this, AddNoticeActivity.class, 2, "TO_CLASS");
                break;

            case R.id.fab_all_students:
                materialDesignFAM.close(true);
                Intent intentC = new Intent(this, AddNoticeActivity.class);
                intentC.putExtra("CAT_ID", 3);
                intentC.putExtra("CAT_NAME", "TO_ALL_STUDENTS");
                startActivityForResult(intentC, 1);
//                Utils.startActivityAfterCleanup(NoticeActivity.this, AddNoticeActivity.class, 3, "TO_ALL_STUDENTS");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setUpStaticData();
            }
        }
    }
}
