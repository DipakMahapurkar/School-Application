package com.schoolapp.apiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dipak on 9/7/17.
 */

public class HomeworkPostRequestModel {
    @SerializedName("subjectid")
    @Expose
    public String subjectid;
    @SerializedName("classid")
    @Expose
    public String classid;
    @SerializedName("divisionid")
    @Expose
    public String divisionid;
    @SerializedName("description")
    @Expose
    public String description;


    public HomeworkPostRequestModel(String subjectid, String classid, String divisionid, String description) {
        super();
        this.subjectid = subjectid;
        this.classid = classid;
        this.divisionid = divisionid;
        this.description = description;
    }
}
