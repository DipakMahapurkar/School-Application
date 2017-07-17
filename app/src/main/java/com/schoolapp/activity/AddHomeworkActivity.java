package com.schoolapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.apiModels.HomeworkPostRequestModel;
import com.schoolapp.apiModels.TimelineResponseModel;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.Utils;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schoolapp.utils.Constant.SUCCESS_STATUS_CODE;

public class AddHomeworkActivity extends AppCompatActivity {

    private static final String TAG = "AddHomeWork Activity";

    Toolbar mAddHomeworkToolbar;
    TextView mToolbarTitle;

    EditText mHomeworkClassNameEdt, mHomeworkDivisionEdt, mHomeworkSubjectEdt, mHomeworkDescriptionEdt;

    ArrayList<String> mClassList = new ArrayList<>();
    ArrayList<String> mDivisionList = new ArrayList<>();
    ArrayList<String> mSubjectList = new ArrayList<>();
    SpinnerDialog mClassSpinnerDialog;
    SpinnerDialog mDivisionSpinnerDialog;
    SpinnerDialog mSubjectSpinnerDialog;

    APICallInterface mApiCallInterface;

    String mClassId, mSubjectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar();

        setHomeWorkData();

        mHomeworkClassNameEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassSpinnerDialog.showSpinerDialog();
            }
        });

        mHomeworkDivisionEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDivisionSpinnerDialog.showSpinerDialog();
            }
        });

        mHomeworkSubjectEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubjectSpinnerDialog.showSpinerDialog();
            }
        });

        mClassSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                Log.d(TAG, "Selected item text " + s + ", Position " + i);
                mClassId = String.valueOf(i + 1);
                mHomeworkClassNameEdt.setText(s);
            }
        });

        mDivisionSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                Log.d(TAG, "Selected item text " + s + ", Position " + i);
                mHomeworkDivisionEdt.setText(s);
            }
        });

        mSubjectSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                Log.d(TAG, "Selected item text " + s + ", Position " + i);
                mSubjectId = String.valueOf(i + 1);
                mHomeworkSubjectEdt.setText(s);
            }
        });
    }

    private void setHomeWorkData() {
        mClassList.add("11");
        mClassList.add("12");
        mDivisionList.add("A");
        mDivisionList.add("B");
        mDivisionList.add("C");
        mDivisionList.add("D");
        mDivisionList.add("E");
        mDivisionList.add("F");
        mDivisionList.add("G");
        mDivisionList.add("H");
        mSubjectList.add("Physics");
        mSubjectList.add("Chemistry");
        mSubjectList.add("Biology");
        mSubjectList.add("Mathematics");
        mSubjectList.add("English");

        mClassSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mClassList, "Select Class", R.style.DialogAnimations_SmileWindow);// With 	Animation
        mDivisionSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mDivisionList, "Select Division", R.style.DialogAnimations_SmileWindow);// With 	Animation
        mSubjectSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mSubjectList, "Select Subject", R.style.DialogAnimations_SmileWindow);// With 	Animation

    }

    private void initialiseViews() {
        mAddHomeworkToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mHomeworkClassNameEdt = (EditText) findViewById(R.id.homework_class_edt);
        mHomeworkDivisionEdt = (EditText) findViewById(R.id.homework_division_edt);
        mHomeworkSubjectEdt = (EditText) findViewById(R.id.homework_subjects_edt);
        mHomeworkDescriptionEdt = (EditText) findViewById(R.id.homework_description_edt);
    }

    private void setupToolbar() {
        if (mAddHomeworkToolbar != null) {
            setSupportActionBar(mAddHomeworkToolbar);
            mToolbarTitle.setText(getString(R.string.str_add_homework));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.submit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                submitHomeWork();
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitHomeWork() {
        String className = mClassId;
        String division = mHomeworkDivisionEdt.getText().toString();
        String subject = mSubjectId;
        String description = mHomeworkDescriptionEdt.getText().toString();

        if (validate(className, division, subject, description)) {
            ProgressDialogUtils.show(this, R.string.loading_message_str);
            mApiCallInterface.homeworkPostAPI(new HomeworkPostRequestModel(subject, className, division, description)).enqueue(new Callback<TimelineResponseModel>() {
                @Override
                public void onResponse(Call<TimelineResponseModel> call, Response<TimelineResponseModel> response) {
                    Log.d(TAG, "Add AddHomeWork response" + response.body());
                    int statusCode = response.body().getStatus();
                    if (statusCode == SUCCESS_STATUS_CODE) {
                        Utils.showToast(AddHomeworkActivity.this, "Homework added successfully !!");
                        finish();
                    } else {
                        Log.e(TAG, "Some thing getting wrong");
                    }
                    ProgressDialogUtils.dismiss();
                }

                @Override
                public void onFailure(Call<TimelineResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, t.getMessage());
                    ProgressDialogUtils.dismiss();
                }
            });
        } else {
            Utils.showToast(this, "Please enter all fields");
        }
    }

    public boolean validate(String className, String division, String subject, String description) {
        boolean valid = true;

        if (className.isEmpty()) {
            mHomeworkClassNameEdt.setError("Please select the Class");
            valid = false;
        } else {
            mHomeworkClassNameEdt.setError(null);
        }

        if (division.isEmpty()) {
            mHomeworkDivisionEdt.setError("Please select the Division");
            valid = false;
        } else {
            mHomeworkDivisionEdt.setError(null);
        }

        if (subject.isEmpty()) {
            mHomeworkSubjectEdt.setError("Please select the Subject");
            valid = false;
        } else {
            mHomeworkSubjectEdt.setError(null);
        }

        if (description.isEmpty()) {
            mHomeworkDescriptionEdt.setError("Please enter the description text");
            valid = false;
        } else {
            mHomeworkDescriptionEdt.setError(null);
        }

        return valid;
    }
}
