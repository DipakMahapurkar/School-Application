package com.schoolapp.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.schoolapp.apiModels.NoticePostAPIBodyRequest;
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

public class AddNoticeActivity extends AppCompatActivity {

    private static final String TAG = "Add Notice Activity";
    Toolbar mAddNoticeToolbar;
    TextView mToolbarTitle;

    EditText mNoticeClassNameEdt, mNoticeDivisionEdt, mNoticeRollNumberEdt, mNoticeDescriptionEdt;
    TextInputLayout mClassTil, mDivisionTil, mRollNoTil;

    APICallInterface mApiCallInterface;

    List<ClassModule> mClassList = new ArrayList<>();
    List<DivisionModule> mDivisionList = new ArrayList<>();
    ArrayList<String> mClassNameList = new ArrayList<>();
    ArrayList<String> mDivisionNameList = new ArrayList<>();
    ArrayList<String> mRollNumberList = new ArrayList<>();
    SpinnerDialog mClassSpinnerDialog;
    SpinnerDialog mDivisionSpinnerDialog;
    SpinnerDialog mRollNumberSpinnerDialog;

    String mNoticeType, mClassId = "", mDivisionId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar();

        if (getBundleValue().equals("TO_STUDENT")) {
            setNoticeData();
            getClassData();
            mClassTil.setVisibility(View.VISIBLE);
            mDivisionTil.setVisibility(View.VISIBLE);
            mRollNoTil.setVisibility(View.VISIBLE);

            mNoticeClassNameEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClassSpinnerDialog.showSpinerDialog();
                }
            });

            mNoticeDivisionEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDivisionSpinnerDialog.showSpinerDialog();
                }
            });

            mNoticeRollNumberEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRollNumberSpinnerDialog.showSpinerDialog();
                }
            });

            mRollNumberSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String s, int i) {
                    mNoticeRollNumberEdt.setText(s);
                }
            });
        } else if (getBundleValue().equals("TO_CLASS")) {
            setNoticeData();
            getClassData();
            mClassTil.setVisibility(View.VISIBLE);
            mDivisionTil.setVisibility(View.VISIBLE);

            mNoticeClassNameEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClassSpinnerDialog.showSpinerDialog();
                }
            });

            mNoticeDivisionEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDivisionSpinnerDialog.showSpinerDialog();
                }
            });
        }
    }

    private void initialiseViews() {
        mAddNoticeToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mClassTil = (TextInputLayout) findViewById(R.id.notice_class_text_input_layout);
        mDivisionTil = (TextInputLayout) findViewById(R.id.notice_division_text_input_layout);
        mRollNoTil = (TextInputLayout) findViewById(R.id.notice_roll_number_text_input_layout);
        mNoticeClassNameEdt = (EditText) findViewById(R.id.notice_class_edt);
        mNoticeDivisionEdt = (EditText) findViewById(R.id.notice_division_edt);
        mNoticeRollNumberEdt = (EditText) findViewById(R.id.notice_roll_number_edt);
        mNoticeDescriptionEdt = (EditText) findViewById(R.id.notice_description_edt);
    }

    private void setupToolbar() {
        if (mAddNoticeToolbar != null) {
            setSupportActionBar(mAddNoticeToolbar);
            mToolbarTitle.setText(getString(R.string.str_add_notice));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private String getBundleValue() {
        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("CAT_NAME");
            mNoticeType = extras.getString("CAT_ID");
        }
        Log.d(TAG, value);
        return value;
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
                        mClassSpinnerDialog = new SpinnerDialog(AddNoticeActivity.this, mClassNameList, "Select Class", R.style.DialogAnimations_SmileWindow);// With 	Animation
                    }
                    mClassSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String s, int i) {
                            Log.d(TAG, "Selected item text " + s + ", Position " + i);
                            mClassId = mClassList.get(i).getId();
                            mNoticeClassNameEdt.setText(s);
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
                        mDivisionSpinnerDialog = new SpinnerDialog(AddNoticeActivity.this, mDivisionNameList, "Select Division", R.style.DialogAnimations_SmileWindow);// With 	Animation
                    }
                    mDivisionSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String s, int i) {
                            Log.d(TAG, "Selected item text " + s + ", Position " + i);
                            mDivisionId = mDivisionList.get(i).getId();
                            mNoticeDivisionEdt.setText(s);
                        }
                    });
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

    private void setNoticeData() {
        mRollNumberList.add("1");
        mRollNumberList.add("2");
        mRollNumberList.add("3");
        mRollNumberList.add("4");
        mRollNumberList.add("5");
        mRollNumberSpinnerDialog = new SpinnerDialog(this, mRollNumberList, "Select Roll Number", R.style.DialogAnimations_SmileWindow);// With 	Animation
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
                submitNoticeData();
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitNoticeData() {
        String className = mClassId;
        String division = mDivisionId;
        String rollNumber = mNoticeRollNumberEdt.getText().toString();
        String description = mNoticeDescriptionEdt.getText().toString();
        if (getBundleValue().equals("TO_STUDENT")) {
            if (validateNoticeTypeOne(className, division, rollNumber, description)) {
                postNoticeData(className, division, rollNumber, description);
            } else {
                Utils.showToast(this, "Please enter all fields");
            }
        } else if (getBundleValue().equals("TO_CLASS")) {
            if (validateNoticeTypeTwo(className, division, description)) {
                postNoticeData(className, division, rollNumber, description);
            } else {
                Utils.showToast(this, "Please enter all fields");
            }
        } else {
            if (validateNoticeTypeThree(description)) {
                postNoticeData(className, division, rollNumber, description);
            } else {
                Utils.showToast(this, "Please enter all fields");
            }
        }
    }

    public void postNoticeData(String className, String division, String rollNumber, String description) {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        mApiCallInterface.noticePostAPI(new NoticePostAPIBodyRequest(description,
                mNoticeType, className, division, rollNumber)).enqueue(new Callback<TimelineResponseModel>() {
            @Override
            public void onResponse(Call<TimelineResponseModel> call, Response<TimelineResponseModel> response) {
                Log.d(TAG, "Add AddHomeWork response" + response.body());
                int statusCode = response.body().getStatus();
                if (statusCode == SUCCESS_STATUS_CODE) {
                    Utils.showToast(AddNoticeActivity.this, "Notice added successfully !!");
                    supportFinishAfterTransition();
                    /*Intent returnIntent = new Intent(AddNoticeActivity.this, NoticeActivity.class);
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();*/
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
    }

    public boolean validateNoticeTypeOne(String className, String division, String rollNumber, String description) {
        boolean valid = true;

        if (className.isEmpty()) {
            mNoticeClassNameEdt.setError("Please select the Class");
            valid = false;
        } else {
            mNoticeClassNameEdt.setError(null);
        }

        if (division.isEmpty()) {
            mNoticeDivisionEdt.setError("Please select the Division");
            valid = false;
        } else {
            mNoticeDivisionEdt.setError(null);
        }

        if (rollNumber.isEmpty()) {
            mNoticeRollNumberEdt.setError("Please select the Subject");
            valid = false;
        } else {
            mNoticeRollNumberEdt.setError(null);
        }

        if (description.isEmpty()) {
            mNoticeDescriptionEdt.setError("Please enter the description text");
            valid = false;
        } else {
            mNoticeDescriptionEdt.setError(null);
        }

        return valid;
    }

    public boolean validateNoticeTypeTwo(String className, String division, String description) {
        boolean valid = true;

        if (className.isEmpty()) {
            mNoticeClassNameEdt.setError("Please select the Class");
            valid = false;
        } else {
            mNoticeClassNameEdt.setError(null);
        }

        if (division.isEmpty()) {
            mNoticeDivisionEdt.setError("Please select the Division");
            valid = false;
        } else {
            mNoticeDivisionEdt.setError(null);
        }

        if (description.isEmpty()) {
            mNoticeDescriptionEdt.setError("Please enter the description text");
            valid = false;
        } else {
            mNoticeDescriptionEdt.setError(null);
        }

        return valid;
    }

    public boolean validateNoticeTypeThree(String description) {
        boolean valid = true;

        if (description.isEmpty()) {
            mNoticeDescriptionEdt.setError("Please enter the description text");
            valid = false;
        } else {
            mNoticeDescriptionEdt.setError(null);
        }

        return valid;
    }

}
