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
import com.schoolapp.apiModels.ClassResponseModule;
import com.schoolapp.apiModels.DivisionResponseModule;
import com.schoolapp.apiModels.HomeworkPostRequestModel;
import com.schoolapp.apiModels.SubjectResponseModule;
import com.schoolapp.apiModels.TimelineResponseModel;
import com.schoolapp.models.ClassModule;
import com.schoolapp.models.DivisionModule;
import com.schoolapp.models.SubjectModule;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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

    List<ClassModule> mClassList = new ArrayList<>();
    List<DivisionModule> mDivisionList = new ArrayList<>();
    List<SubjectModule> mSubjectList = new ArrayList<>();
    ArrayList<String> mClassNameList = new ArrayList<>();
    ArrayList<String> mSubjectNameList = new ArrayList<>();
    ArrayList<String> mDivisionNameList = new ArrayList<>();
    SpinnerDialog mClassSpinnerDialog;
    SpinnerDialog mDivisionSpinnerDialog;
    SpinnerDialog mSubjectSpinnerDialog;

    APICallInterface mApiCallInterface;

    String mClassId, mDivisionId, mSubjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar();

        getClassData();
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
    }

    private void getClassData() {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        mApiCallInterface.getClassAPI().enqueue(new Callback<ClassResponseModule>() {
            @Override
            public void onResponse(Call<ClassResponseModule> call, Response<ClassResponseModule> response) {
                Log.d(TAG, "Subject response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE && response.body().getData() != null && response.body().getData().size() >= 0) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        mClassList.add(new ClassModule(response.body().getData().get(i).getId(), response.body().getData().get(i).getClassName()));
                        mClassNameList.add(response.body().getData().get(i).getClassName());
                        mClassSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mClassNameList, "Select Class", R.style.DialogAnimations_SmileWindow);// With 	Animation
                    }
                    mClassSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String s, int i) {
                            Log.d(TAG, "Selected item text " + s + ", Position " + i);
                            mClassId = mClassList.get(i).getId();
                            mHomeworkClassNameEdt.setText(s);
                        }
                    });
                    getDivisionData();
                } else {
                    Log.e(TAG, "Some thing getting wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<ClassResponseModule> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
                ProgressDialogUtils.dismiss();
            }
        });
    }

    private void getDivisionData() {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        mApiCallInterface.getDivisionAPI().enqueue(new Callback<DivisionResponseModule>() {
            @Override
            public void onResponse(Call<DivisionResponseModule> call, Response<DivisionResponseModule> response) {
                Log.d(TAG, "Subject response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE && response.body().getData() != null && response.body().getData().size() >= 0) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        mDivisionList.add(new DivisionModule(response.body().getData().get(i).getId(), response.body().getData().get(i).getDivision()));
                        mDivisionNameList.add(response.body().getData().get(i).getDivision());
                        mDivisionSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mDivisionNameList, "Select Division", R.style.DialogAnimations_SmileWindow);// With 	Animation
                    }
                    mDivisionSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String s, int i) {
                            Log.d(TAG, "Selected item text " + s + ", Position " + i);
                            mDivisionId = mDivisionList.get(i).getId();
                            mHomeworkDivisionEdt.setText(s);
                        }
                    });
                    getSubjectData();
                } else {
                    Log.e(TAG, "Some thing getting wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<DivisionResponseModule> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
                ProgressDialogUtils.dismiss();
            }
        });
    }

    private void getSubjectData() {
        mApiCallInterface.getSubjectsAPI().enqueue(new Callback<SubjectResponseModule>() {
            @Override
            public void onResponse(Call<SubjectResponseModule> call, Response<SubjectResponseModule> response) {
                Log.d(TAG, "Subject response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE && response.body().getData() != null && response.body().getData().size() >= 0) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        mSubjectList.add(new SubjectModule(response.body().getData().get(i).getId(), response.body().getData().get(i).getSubjectName()));
                        mSubjectNameList.add(response.body().getData().get(i).getSubjectName());
                        mSubjectSpinnerDialog = new SpinnerDialog(AddHomeworkActivity.this, mSubjectNameList, "Select Subject", R.style.DialogAnimations_SmileWindow);// With 	Animation
                    }
                    mSubjectSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String s, int i) {
                            Log.d(TAG, "Selected item text " + s + ", Position " + i);
                            mSubjectId = mSubjectList.get(i).getId();
                            mHomeworkSubjectEdt.setText(s);
                        }
                    });
                } else {
                    Log.e(TAG, "Some thing getting wrong");
                }
                ProgressDialogUtils.dismiss();
            }

            @Override
            public void onFailure(Call<SubjectResponseModule> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
                ProgressDialogUtils.dismiss();
            }
        });
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
