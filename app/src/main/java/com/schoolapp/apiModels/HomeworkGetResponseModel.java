package com.schoolapp.apiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.schoolapp.models.HomeworkModel;

import java.util.List;


public class HomeworkGetResponseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<HomeworkModel> homeworkList = null;


    public HomeworkGetResponseModel(Integer status, String statusMessage, List<HomeworkModel> data) {
        super();
        this.status = status;
        this.statusMessage = statusMessage;
        this.homeworkList = data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<HomeworkModel> getData() {
        return homeworkList;
    }
}
