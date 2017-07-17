package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dipak on 8/7/17.
 */

public class TeacherDataModel {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("teacher_name")
    @Expose
    public String teacherName;
    @SerializedName("teacher_email")
    @Expose
    public String teacherEmail;
    @SerializedName("teacher_password")
    @Expose
    public String teacherPassword;
    @SerializedName("teacher_dob")
    @Expose
    public String teacherDob;
    @SerializedName("teacher_qualification")
    @Expose
    public String teacherQualification;

    public String getId() {
        return id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public String getTeacherDob() {
        return teacherDob;
    }

    public String getTeacherQualification() {
        return teacherQualification;
    }
}
