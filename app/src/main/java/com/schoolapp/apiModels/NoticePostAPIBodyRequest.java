package com.schoolapp.apiModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticePostAPIBodyRequest {
    @SerializedName("notice_description")
    @Expose
    public String noticeDescription;
    @SerializedName("notice_type")
    @Expose
    public String noticeType;
    @SerializedName("class_id")
    @Expose
    public String classId;
    @SerializedName("division_id")
    @Expose
    public String divisionId;
    @SerializedName("student_id")
    @Expose
    public String studentId;

    public NoticePostAPIBodyRequest(String noticeDescription, String noticeType, String classId, String divisionId, String studentId) {
        super();
        this.noticeDescription = noticeDescription;
        this.noticeType = noticeType;
        this.classId = classId;
        this.divisionId = divisionId;
        this.studentId = studentId;
    }
}
