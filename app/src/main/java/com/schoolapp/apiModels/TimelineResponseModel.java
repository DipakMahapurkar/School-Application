package com.schoolapp.apiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipak on 8/7/17.
 */

public class TimelineResponseModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("status_message")
    @Expose
    public String statusMessage;
    @SerializedName("data")
    @Expose
    public List<JSONArray> data = new ArrayList<>();

    public Integer getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public List<JSONArray> getData() {
        return data;
    }
}
