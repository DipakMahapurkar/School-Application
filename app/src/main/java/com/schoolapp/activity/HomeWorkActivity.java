package com.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.schoolapp.R;
import com.schoolapp.adapters.HomeworkAdapter;
import com.schoolapp.apiModels.HomeworkGetResponseModel;
import com.schoolapp.models.HomeworkModel;
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

public class HomeWorkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Homework Activity";
    Toolbar mHomeWorkToolbar;
    TextView mToolbarTitle;
    RecyclerView mHomeworkRecyclerView;
    View mResultNotFoundLayout;

    FloatingActionButton mAddHomeworkFab;

    List<HomeworkModel> mHomeworkList;

    APICallInterface mApiCallInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar(getBundleValue());

        staticHomeworkData();
    }

    private void initialiseViews() {
        mHomeWorkToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mHomeworkRecyclerView = (RecyclerView) findViewById(R.id.timeline_recycler_view);
        mAddHomeworkFab = (FloatingActionButton) findViewById(R.id.idAddHomewworkFab);
        mHomeworkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomeworkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mResultNotFoundLayout = findViewById(R.id.layout_no_result);
        mResultNotFoundLayout.setVisibility(View.GONE);

        if (Constant.USER_ROLE.equals("Teacher")) {
            mAddHomeworkFab.setVisibility(View.VISIBLE);
        }

        mHomeworkRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Constant.USER_ROLE.equals("Teacher")) {
                    if (dy > 0)
                        mAddHomeworkFab.hide();
                    else if (dy < 0)
                        mAddHomeworkFab.show();
                }
            }
        });

        mAddHomeworkFab.setOnClickListener(this);
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
        if (mHomeWorkToolbar != null) {
            setSupportActionBar(mHomeWorkToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void staticHomeworkData() {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        /*mHomeworkList = new ArrayList<>();
        mHomeworkList.add(new HomeworkModel(1, "Math", "2-May-2017", "Solve the unit 3 problems given in unsolved section."));
        mHomeworkList.add(new HomeworkModel(2, "English", "4-May-2017", "Read the unit 2 and understand the what want to say author."));
        mHomeworkList.add(new HomeworkModel(3, "Geography", "6-May-2017", "Read and remember the state in India and it's capital."));
        mHomeworkList.add(new HomeworkModel(4, "History", "8-May-2017", "Read the 1st war and remember important date and places of war."));
        mHomeworkList.add(new HomeworkModel(5, "Science", "10-May-2017", "Remember the formulas of solid, liquid and air materials."));
        mHomeworkList.add(new HomeworkModel(6, "Marathi", "12-May-2017", "Read the Grammar of marathi sentences and solve the examples."));
        mHomeworkList.add(new HomeworkModel(7, "Sanskrit", "14-May-2017", "Remember the paragraphs of Sanskrit."));
        mHomeworkList.add(new HomeworkModel(8, "Hindi", "16-May-2017", "Read and remember the phrases of hindi."));*/

        mApiCallInterface.homeworkGetAPI(SharedPreference.getString(this, "ID")).enqueue(new Callback<HomeworkGetResponseModel>() {
            @Override
            public void onResponse(Call<HomeworkGetResponseModel> call, Response<HomeworkGetResponseModel> response) {
                Log.d(TAG, "Response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE) {
                    mHomeworkList = new ArrayList<>();
                    if (response.body().getData() != null && response.body().getData().size() >= 0) {
                        mResultNotFoundLayout.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            mHomeworkList.add(new HomeworkModel(response.body().getData().get(i).getId(),
                                    response.body().getData().get(i).getSubjectName(),
                                    changeDateFormats(response.body().getData().get(i).getCreatedAt()),
                                    response.body().getData().get(i).getHomeworkDescription(),
                                    response.body().getData().get(i).getSubjectId(),
                                    response.body().getData().get(i).getClassId(),
                                    response.body().getData().get(i).getDivisionId()));
                        }
                        mHomeworkRecyclerView.setAdapter(new HomeworkAdapter(HomeWorkActivity.this, mHomeworkList));
                    } else {
                        mResultNotFoundLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    Utils.showToast(HomeWorkActivity.this, "Some thing get wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<HomeworkGetResponseModel> call, Throwable t) {
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
            case R.id.idAddHomewworkFab:
                startActivity(new Intent(this, AddHomeworkActivity.class));
                break;
        }
    }
}
