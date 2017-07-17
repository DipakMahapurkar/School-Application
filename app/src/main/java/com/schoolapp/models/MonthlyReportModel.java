package com.schoolapp.models;


public class MonthlyReportModel {

    private int monthId;
    private String mothName, gradeName;

    public MonthlyReportModel(int monthId, String mothName, String gradeName) {
        this.monthId = monthId;
        this.mothName = mothName;
        this.gradeName = gradeName;
    }

    public int getMonthId() {
        return monthId;
    }

    public String getMothName() {
        return mothName;
    }

    public String getGradeName() {
        return gradeName;
    }
}
