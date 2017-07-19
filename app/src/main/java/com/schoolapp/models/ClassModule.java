package com.schoolapp.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassModule {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("class")
    @Expose
    private String className;
    
    public ClassModule(String id, String className) {
        super();
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
