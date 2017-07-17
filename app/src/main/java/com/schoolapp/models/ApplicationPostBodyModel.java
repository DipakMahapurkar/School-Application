package com.schoolapp.models;

/**
 * Created by dipak on 8/7/17.
 */

public class ApplicationPostBodyModel {
    public String studentid;
    public String classid;
    public String divisionid;
    public String description;

    public ApplicationPostBodyModel(String studentid, String classid, String divisionid, String description) {
        this.studentid = studentid;
        this.classid = classid;
        this.divisionid = divisionid;
        this.description = description;
    }
}
