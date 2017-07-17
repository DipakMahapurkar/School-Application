package com.schoolapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeworkModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("homework_description")
    @Expose
    private String homeworkDescription;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("division_id")
    @Expose
    private String divisionId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("Subject_Name")
    @Expose
    private String subjectName;

    public HomeworkModel(String id, String subjectName, String createdAt, String homeworkDescription, String subjectId, String classId, String divisionId) {
        super();
        this.id = id;
        this.homeworkDescription = homeworkDescription;
        this.subjectId = subjectId;
        this.classId = classId;
        this.divisionId = divisionId;
        this.createdAt = createdAt;
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeworkDescription() {
        return homeworkDescription;
    }

    public void setHomeworkDescription(String homeworkDescription) {
        this.homeworkDescription = homeworkDescription;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
