package com.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.schoolapp.R;
import com.schoolapp.models.StudentResponseModel;
import com.schoolapp.models.TeacherResponseModel;
import com.schoolapp.models.UserDataModel;
import com.schoolapp.models.UserLoginPostDataModel;
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
import static com.schoolapp.utils.Utils.isValidateEmail;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login Activity";
    // UI references.
    Button mEmailSignInButton;
    Button mParentEmailSignInButton;
    EditText mEmailView;
    EditText mPasswordView;
    APICallInterface mApiCallInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiCallInterface = APIUtils.getSOService();
        initialiseViews();

        singleTimeLogin();

        mEmailSignInButton.setOnClickListener(this);
        mParentEmailSignInButton.setOnClickListener(this);
    }

    private void singleTimeLogin() {
        if (!SharedPreference.getString(this, "ID").isEmpty() && !SharedPreference.getString(this, "NAME").isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void initialiseViews() {
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.teacher_email_sign_in_button);
        mParentEmailSignInButton = (Button) findViewById(R.id.parent_email_sign_in_button);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.teacher_email_sign_in_button) {
            if (Utils.isNetworkAvailable(this)) {
                login("Teacher");
            } else {
                Utils.showToast(this, NO_INTERNET_MESSAGE);
            }
        }

        if (view.getId() == R.id.parent_email_sign_in_button) {
            if (Utils.isNetworkAvailable(this)) {
                login("Parent");
            } else {
                Utils.showToast(this, NO_INTERNET_MESSAGE);
            }
        }
    }

    public void login(String userRole) {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
        } else {
            onLoginSuccess(userRole);
        }
    }

    public void onLoginSuccess(final String userRole) {
        ProgressDialogUtils.show(this, R.string.loading_message_str);
        String email = mEmailView.getText().toString();
        String md5Password = Utils.md5PasswordEncryption(mPasswordView.getText().toString());
        Log.d(TAG, "MD5 Password: " + md5Password);

        if (userRole.equals("Teacher")) {
            mApiCallInterface.loginTeacherApiCall(new UserLoginPostDataModel(email, md5Password)).enqueue(new Callback<TeacherResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<TeacherResponseModel> call, @NonNull Response<TeacherResponseModel> response) {
                    Log.d(TAG, "Response" + response.body());
                    int statusCode = response.body().getStatus();
                    if (statusCode == SUCCESS_STATUS_CODE) {
                        try {
                            for (int i = 0; i < response.body().getTeacherData().size(); i++) {
                                SharedPreference.setString(LoginActivity.this, "ID", response.body().getTeacherData().get(i).getId());
                                SharedPreference.setString(LoginActivity.this, "NAME", response.body().getTeacherData().get(i).getTeacherName());
                                SharedPreference.setString(LoginActivity.this, "DOB", response.body().getTeacherData().get(i).getTeacherEmail());
                                SharedPreference.setString(LoginActivity.this, "QUALIFICATION", response.body().getTeacherData().get(i).getTeacherQualification());
                                SharedPreference.setString(LoginActivity.this, "USER_ROLE", userRole);
                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Array is empty" + e.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Some thing getting wrong");
                    }
                    ProgressDialogUtils.dismiss();
                }

                @Override
                public void onFailure(Call<TeacherResponseModel> call, Throwable t) {
                    Log.e(TAG, "Something getting wrong");
                    t.printStackTrace();
                    ProgressDialogUtils.dismiss();
                }
            });
        } else {
            mApiCallInterface.loginUserApiCall(new UserLoginPostDataModel(email, md5Password)).enqueue(new Callback<StudentResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<StudentResponseModel> call, @NonNull Response<StudentResponseModel> response) {
                    Log.d(TAG, "Response" + response.body());
                    int statusCode = response.body().getStatus();
                    if (statusCode == SUCCESS_STATUS_CODE) {
                        try {
                            for (int i = 0; i < response.body().getStudentData().size(); i++) {
                                SharedPreference.setString(LoginActivity.this, "ID", response.body().getStudentData().get(i).getId());
                                SharedPreference.setString(LoginActivity.this, "NAME", response.body().getStudentData().get(i).getStudentName());
                                SharedPreference.setString(LoginActivity.this, "CLASS", response.body().getStudentData().get(i).getStudentClass());
                                SharedPreference.setString(LoginActivity.this, "ROLL_NUMBER", response.body().getStudentData().get(i).getStudentRollNumber());
                                SharedPreference.setString(LoginActivity.this, "DOB", response.body().getStudentData().get(i).getStudentDob());
                                SharedPreference.setString(LoginActivity.this, "PARENT_NAME", response.body().getStudentData().get(i).getStudentParentName());
                                SharedPreference.setString(LoginActivity.this, "MOBILE_NUMBER", response.body().getStudentData().get(i).getStudentMobile());
                                SharedPreference.setString(LoginActivity.this, "EMAIL", response.body().getStudentData().get(i).getStudentEmail());
                                SharedPreference.setString(LoginActivity.this, "PHOTO", response.body().getStudentData().get(i).getStudentPhoto());
                                SharedPreference.setString(LoginActivity.this, "DIVISION", response.body().getStudentData().get(i).getStudentDivision());
                                SharedPreference.setString(LoginActivity.this, "USER_ROLE", userRole);
                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            Log.d(TAG, "Array is empty" + e.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Some thing getting wrong");
                    }
                    ProgressDialogUtils.dismiss();
                }

                @Override
                public void onFailure(Call<StudentResponseModel> call, Throwable t) {
                    Log.e(TAG, "Something getting wrong");
                    t.printStackTrace();
                    ProgressDialogUtils.dismiss();
                }
            });
        }
    }

    public void onLoginFailed() {
        Utils.showToast(this, "Login Failed !!");
    }

    public boolean validate() {
        boolean valid = true;

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.isEmpty() || !isValidateEmail(email)) {
            mEmailView.setError("Enter a valid email address");
            valid = false;
        } else {
            mEmailView.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordView.setError("Password between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordView.setError(null);
        }

        return valid;
    }
}

