package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dipak on 8/7/17.
 */

public class TimelineRequestBodyModel {
    @SerializedName("teacherid")
    @Expose
    public String teacherId;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("timelinetext")
    @Expose
    public String timelineText;

    @SerializedName("eventdate")
    @Expose
    public String eventDate;

    public TimelineRequestBodyModel(String teacherId, String eventDate, String image, String timelineText) {
        this.teacherId = teacherId;
        this.eventDate = eventDate;
        this.image = image;
        this.timelineText = timelineText;
    }
}
