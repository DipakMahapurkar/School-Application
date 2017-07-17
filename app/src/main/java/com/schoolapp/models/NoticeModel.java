package com.schoolapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeModel {
    private String noticeTitle;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("notice_description")
    @Expose
    private String noticeDescription;
    @SerializedName("notice_type")
    @Expose
    private String noticeType;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public NoticeModel(String id, String noticeTitle, String noticeDescription, String noticeType, String classId, String divisionId, String studentId, String createdAt) {
        super();
        this.id = id;
        this.noticeTitle = noticeTitle;
        this.noticeDescription = noticeDescription;
        this.noticeType = noticeType;
        this.classId = classId;
        this.divisionId = divisionId;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
