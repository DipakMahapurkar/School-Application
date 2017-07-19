package com.schoolapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by snap90 on 19/7/17.
 */

public class DivisionModule {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("division")
    @Expose
    private String division;

    public DivisionModule(String id, String division) {
        super();
        this.id = id;
        this.division = division;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
