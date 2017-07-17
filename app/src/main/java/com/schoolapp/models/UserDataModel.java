package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDataModel implements Serializable {

    public UserDataModel(String id, String studentName, String studentClass, String studentRollNumber, String studentDob, String studentParentName, String studentMobile, String studentEmail, String studentPhoto, String studentDivision) {
        this.id = id;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentRollNumber = studentRollNumber;
        this.studentDob = studentDob;
        this.studentParentName = studentParentName;
        this.studentMobile = studentMobile;
        this.studentEmail = studentEmail;
        this.studentPhoto = studentPhoto;
        this.studentDivision = studentDivision;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("student_name")
    @Expose
    private String studentName;

    @SerializedName("student_class")
    @Expose
    private String studentClass;

    @SerializedName("student_roll_number")
    @Expose
    private String studentRollNumber;

    @SerializedName("student_dob")
    @Expose
    private String studentDob;

    @SerializedName("student_parent_name")
    @Expose
    private String studentParentName;

    @SerializedName("student_mobile")
    @Expose
    private String studentMobile;

    @SerializedName("student_email")
    @Expose
    private String studentEmail;

    @SerializedName("student_photo")
    @Expose
    private String studentPhoto;

    @SerializedName("student_division")
    @Expose
    private String studentDivision;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentRollNumber() {
        return studentRollNumber;
    }

    public void setStudentRollNumber(String studentRollNumber) {
        this.studentRollNumber = studentRollNumber;
    }

    public String getStudentDob() {
        return studentDob;
    }

    public void setStudentDob(String studentDob) {
        this.studentDob = studentDob;
    }

    public String getStudentParentName() {
        return studentParentName;
    }

    public void setStudentParentName(String studentParentName) {
        this.studentParentName = studentParentName;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

    public String getStudentDivision() {
        return studentDivision;
    }

    public void setStudentDivision(String studentDivision) {
        this.studentDivision = studentDivision;
    }
}
