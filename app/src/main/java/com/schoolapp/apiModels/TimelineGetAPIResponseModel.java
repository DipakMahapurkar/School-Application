package com.schoolapp.apiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.schoolapp.models.TimelineListModel;

import java.util.ArrayList;
import java.util.List;


public class TimelineGetAPIResponseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<TimelineListModel> timelineList = new ArrayList();

    public TimelineGetAPIResponseModel(Integer status, String statusMessage, List<TimelineListModel> data) {
        super();
        this.status = status;
        this.statusMessage = statusMessage;
        this.timelineList = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<TimelineListModel> getData() {
        return timelineList;
    }

    public void setData(List<TimelineListModel> data) {
        this.timelineList = data;
    }
}
