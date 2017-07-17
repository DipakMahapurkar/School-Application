package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineListModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("timeline_text")
    @Expose
    private String timelineText;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("timeline_image")
    @Expose
    private String timelineImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("teacher_name")
    @Expose
    private String teacherName;

    public TimelineListModel(String id, String timelineText, String teacherId, String timelineImage, String createdAt, String teacherName) {
        super();
        this.id = id;
        this.timelineText = timelineText;
        this.teacherId = teacherId;
        this.timelineImage = timelineImage;
        this.createdAt = createdAt;
        this.teacherName = teacherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimelineText() {
        return timelineText;
    }

    public void setTimelineText(String timelineText) {
        this.timelineText = timelineText;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTimelineImage() {
        return timelineImage;
    }

    public void setTimelineImage(String timelineImage) {
        this.timelineImage = timelineImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
