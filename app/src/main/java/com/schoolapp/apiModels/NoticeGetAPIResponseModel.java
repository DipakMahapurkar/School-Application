package com.schoolapp.apiModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.schoolapp.models.NoticeModel;

import java.util.ArrayList;
import java.util.List;

public class NoticeGetAPIResponseModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<NoticeModel> noticeListData = new ArrayList<>();

    public Integer getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<NoticeModel> getData() {
        return noticeListData;
    }
}
