package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectModule {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("Subject_Name")
    @Expose
    private String subjectName;
    
    public SubjectModule(String id, String subjectName) {
        super();
        this.id = id;
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
