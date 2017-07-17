package com.schoolapp.models;


public class DashboardCatListModel {
    int catId, resourceId;
    String catName;

    public DashboardCatListModel(int catId, int resourceId, String catName) {
        this.catId = catId;
        this.resourceId = resourceId;
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getCatName() {
        return catName;
    }
}
