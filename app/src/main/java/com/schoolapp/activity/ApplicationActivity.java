package com.schoolapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.models.ApplicationPostBodyModel;
import com.schoolapp.models.StudentResponseModel;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.SharedPreference;
import com.schoolapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.schoolapp.utils.Constant.NO_INTERNET_MESSAGE;
import static com.schoolapp.utils.Constant.SUCCESS_STATUS_CODE;

public class ApplicationActivity extends AppCompatActivity {

    private static final String TAG = "Application Activity";
    Toolbar mApplicationToolbar;
    TextView mToolbarTitle;
    EditText mApplicationEdt;

    APICallInterface mApiCallInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        mApiCallInterface = APIUtils.getSOService();

        initialiseViews();

        setupToolbar(getBundleValue());
    }

    private void initialiseViews() {
        mApplicationToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mToolbarTitle = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mApplicationEdt = (EditText) findViewById(R.id.application_edt);
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
        if (mApplicationToolbar != null) {
            setSupportActionBar(mApplicationToolbar);
            mToolbarTitle.setText(toolbarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                if (Utils.isNetworkAvailable(this))
                    submitApplication();
                else
                    Utils.showToast(this, NO_INTERNET_MESSAGE);
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitApplication() {
        String applicationText = mApplicationEdt.getText().toString();
        if (validate(applicationText)) {
            ProgressDialogUtils.show(this, R.string.loading_message_str);
            mApiCallInterface.applicationPostAPI(new ApplicationPostBodyModel(
                    SharedPreference.getString(this, "ID"),
                    SharedPreference.getString(this, "CLASS"),
                    SharedPreference.getString(this, "DIVISION"),
                    applicationText)).enqueue(new Callback<StudentResponseModel>() {
                @Override
                public void onResponse(Call<StudentResponseModel> call, Response<StudentResponseModel> response) {
                    Log.d(TAG, "Response" + response.body());
                    int statusCode = response.body().getStatus();
                    if (statusCode == SUCCESS_STATUS_CODE) {
                        Utils.showToast(ApplicationActivity.this, "Application Submitted Successfully !!");
                        finish();
                    } else {
                        Utils.showToast(ApplicationActivity.this, "Some thing get wrong");
                    }
                    ProgressDialogUtils.dismiss();
                }

                @Override
                public void onFailure(Call<StudentResponseModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, t.getMessage());
                    ProgressDialogUtils.dismiss();
                }
            });
        } else {
            Utils.showToast(this, "Application text field is should not be empty");
        }
    }

    public boolean validate(String applicationText) {
        boolean valid = true;

        if (applicationText.isEmpty()) {
            mApplicationEdt.setError("Please enter the application text");
            valid = false;
        } else {
            mApplicationEdt.setError(null);
        }

        return valid;
    }
}
