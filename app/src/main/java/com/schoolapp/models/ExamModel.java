package com.schoolapp.models;

/**
 * Created by dipak on 6/5/17.
 */

public class ExamModel {

    int examId;
    String examName;

    public ExamModel(int examId, String examName) {
        this.examId = examId;
        this.examName = examName;
    }

    public int getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }
}
