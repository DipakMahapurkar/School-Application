package com.schoolapp.apiModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.schoolapp.models.SubjectModule;

import java.util.List;

public class SubjectResponseModule {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("data")
    @Expose
    private List<SubjectModule> data = null;

    public SubjectResponseModule(Integer status, String statusMessage, List<SubjectModule> data) {
        super();
        this.status = status;
        this.statusMessage = statusMessage;
        this.data = data;
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

    public List<SubjectModule> getData() {
        return data;
    }

    public void setData(List<SubjectModule> data) {
        this.data = data;
    }
}
