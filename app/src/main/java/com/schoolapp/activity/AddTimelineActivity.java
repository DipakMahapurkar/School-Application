package com.schoolapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.master.glideimageview.GlideImageView;
import com.master.permissionhelper.PermissionHelper;
import com.schoolapp.R;
import com.schoolapp.apiModels.TimelineResponseModel;
import com.schoolapp.models.TimelineRequestBodyModel;
import com.schoolapp.network.APICallInterface;
import com.schoolapp.network.APIUtils;
import com.schoolapp.utils.ProgressDialogUtils;
import com.schoolapp.utils.SharedPreference;
import com.schoolapp.utils.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import me.nereo.multi_image_selector.MultiImageSelector;
import retrofit2.Call;
import retrofit2.Response;

import static com.schoolapp.utils.Constant.NO_INTERNET_MESSAGE;
import static com.schoolapp.utils.Constant.SUCCESS_STATUS_CODE;


public class AddTimelineActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, PermissionHelper.PermissionCallback {

    private static final String TAG = "Add Timeline Activity";
    ArrayList<String> photoPaths = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;

    Toolbar mAddTimelineToolbar;
    TextView mAddTimelineToolbarTextView;
    EditText mEventTitleEdt, mEventDateEdt;
    TextInputLayout mTilSelectedDate, mTilEventTitle;
    Button mSelectMedia;
    //    ImageView mSelectedImgView;
    GlideImageView mSelectedImgView;
    PermissionHelper mPermissionHelper;

    APICallInterface mApiCallInterface;

    String base64ImageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timeline);

        mApiCallInterface = APIUtils.getSOService();

        initialiseView();

        setupToolbar();

        // Date Select on edit text
        mEventDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddTimelineActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        mSelectMedia.setOnClickListener(this);

    }

    private void initialiseView() {
        mAddTimelineToolbar = (Toolbar) findViewById(R.id.idMainToolbar);
        mAddTimelineToolbarTextView = (TextView) findViewById(R.id.idToolbarTitleTxt);
        mTilSelectedDate = (TextInputLayout) findViewById(R.id.idDateTextInputLayout);
        mEventTitleEdt = (EditText) findViewById(R.id.idEventTitle);
        mTilEventTitle = (TextInputLayout) findViewById(R.id.idTilEventTitle);
        mEventDateEdt = (EditText) findViewById(R.id.idEventDate);
        mSelectMedia = (Button) findViewById(R.id.idSelectMediaBtn);
        mSelectedImgView = (GlideImageView) findViewById(R.id.idSelectedMediaImgView);
    }

    private void setupToolbar() {
        if (mAddTimelineToolbar != null) {
            setSupportActionBar(mAddTimelineToolbar);
            mAddTimelineToolbarTextView.setText(getString(R.string.str_add_event_timeline));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idSelectMediaBtn:
                getPermissions();
                break;
        }
    }

    private void getPermissions() {
        mPermissionHelper = new PermissionHelper(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        mPermissionHelper.request(this);
    }

    public void onPickPhoto() {
        MultiImageSelector.create()
                .showCamera(true)// show camera or not. true by default
                .count(1) // max select image size, 9 by default. used width #.multi()
                .origin(photoPaths) // original select data set, used width #.multi()
                .start(this, REQUEST_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK && data != null) {
                    photoPaths = new ArrayList<>();
                    photoPaths = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    mSelectedImgView.loadImageUrl(photoPaths.get(0));
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_submit:
                if (Utils.isNetworkAvailable(this)) {
                    addTimeline(mEventDateEdt.getText().toString(), mEventTitleEdt.getText().toString());
                } else {
                    Utils.showToast(this, NO_INTERNET_MESSAGE);
                }
                return true;
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTimeline(String date, String description) {
        if (validate(date, description)) {
            ProgressDialogUtils.show(this, R.string.loading_message_str);
            base64ImageString = Utils.encodeImage(photoPaths.get(0));
            mApiCallInterface.timelinePostAPI(new TimelineRequestBodyModel(
                    SharedPreference.getString(this, "ID"), date, base64ImageString, description)).enqueue(new retrofit2.Callback<TimelineResponseModel>() {
                @Override
                public void onResponse(Call<TimelineResponseModel> call, Response<TimelineResponseModel> response) {
                    Log.d(TAG, "Add timeline response" + response.body());
                    int statusCode = response.body().getStatus();
                    if (statusCode == SUCCESS_STATUS_CODE) {
                        Utils.showToast(AddTimelineActivity.this, "Timeline added successfully !!");
                        Intent returnIntent = new Intent(AddTimelineActivity.this, TimelineActivity.class);
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    } else {
                        Log.e(TAG, "Some thing getting wrong");
                    }
                    ProgressDialogUtils.dismiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, t.getMessage());
                    ProgressDialogUtils.dismiss();
                }
            });
        } else {
            Utils.showToast(this, "Please fill all the fields");
        }
    }

    public boolean validate(String date, String description) {
        boolean valid = true;

        if (date.isEmpty()) {
            mEventDateEdt.setError("Please select the timeline date");
            valid = false;
        } else {
            mEventDateEdt.setError(null);
        }

        if (description.isEmpty()) {
            mEventTitleEdt.setError("Please enter the timeline text");
            valid = false;
        } else {
            mEventTitleEdt.setError(null);
        }

        if (photoPaths.size() < 0) {
            Utils.showToast(this, "Please select timeline photo");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionHelper != null) {
            mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        mEventDateEdt.setText(Utils.changeDateFormat(date));
    }

    @Override
    public void onPermissionGranted() {
        onPickPhoto();
    }

    @Override
    public void onPermissionDenied() {
        Log.d(TAG, "Permission Denied");
        Utils.showToast(this, "Permission Denied");
    }

    @Override
    public void onPermissionDeniedBySystem() {
        Log.d(TAG, "Permission Denied By System");
        Utils.showToast(this, "Permission Denied By System");
    }

}
