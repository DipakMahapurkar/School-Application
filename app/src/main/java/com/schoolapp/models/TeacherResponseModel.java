package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipak on 8/7/17.
 */

public class TeacherResponseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    @SerializedName("data")
    @Expose
    private List<TeacherDataModel> teacherData = new ArrayList<>();

    public List<TeacherDataModel> getTeacherData() {
        return teacherData;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
