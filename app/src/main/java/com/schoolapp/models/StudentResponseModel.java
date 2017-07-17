package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snap90 on 5/7/17.
 */

public class StudentResponseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    @SerializedName("data")
    @Expose
    private List<UserDataModel> studentData = new ArrayList<>();

    public String getStatusMessage() {
        return statusMessage;
    }
    public List<UserDataModel> getStudentData() {
        return studentData;
    }

    public Integer getStatus() {
        return status;
    }

}
