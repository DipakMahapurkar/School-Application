package com.schoolapp.network;

/**
 * Created by dipak on 19/3/17.
 */


import com.schoolapp.apiModels.HomeworkGetResponseModel;
import com.schoolapp.apiModels.HomeworkPostRequestModel;
import com.schoolapp.apiModels.NoticeGetAPIResponseModel;
import com.schoolapp.apiModels.NoticePostAPIBodyRequest;
import com.schoolapp.apiModels.TimelineGetAPIResponseModel;
import com.schoolapp.apiModels.TimelineResponseModel;
import com.schoolapp.models.ApplicationPostBodyModel;
import com.schoolapp.models.StudentResponseModel;
import com.schoolapp.models.TeacherResponseModel;
import com.schoolapp.models.TimelineRequestBodyModel;
import com.schoolapp.models.UserLoginPostDataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APICallInterface {

//    @GET("mobileAPI.php?func=search&")
//    Call<> getSearchResultList(@Query("keyword") CharSequence keyword);

    @Headers("Content-Type: application/json")
    @POST("studentapi.php/login")
    Call<StudentResponseModel> loginUserApiCall(@Body UserLoginPostDataModel userLoginPostDataModel);

    @Headers("Content-Type: application/json")
    @POST("teacherapi.php/login")
    Call<TeacherResponseModel> loginTeacherApiCall(@Body UserLoginPostDataModel userLoginPostDataModel);


    @Headers("Content-Type: application/json")
    @POST("applicationapi.php")
    Call<StudentResponseModel> applicationPostAPI(@Body ApplicationPostBodyModel applicationPostBodyModel);

    @Headers("Content-Type: application/json")

    @POST("timelineapi.php")
    Call<TimelineResponseModel> timelinePostAPI(@Body TimelineRequestBodyModel timelineRequestBodyModel);

    @Headers("Content-Type: application/json")
    @GET("timelineapi.php")
    Call<TimelineGetAPIResponseModel> getTimelineAPI();

    @Headers("Content-Type: application/json")
    @POST("homeworkapi.php")
    Call<TimelineResponseModel> homeworkPostAPI(@Body HomeworkPostRequestModel homeworkPostRequestModel);

    @Headers("Content-Type: application/json")
    @GET("homeworkapi.php/{student_id}")
    Call<HomeworkGetResponseModel> homeworkGetAPI(@Path("student_id") String id);

    @Headers("Content-Type: application/json")
    @GET("noticeapi.php/{student_id}")
    Call<NoticeGetAPIResponseModel> noticeGetAPI(@Path("student_id") String id);

    @Headers("Content-Type: application/json")
    @POST("noticeapi.php")
    Call<TimelineResponseModel> noticePostAPI(@Body NoticePostAPIBodyRequest noticePostAPIBodyRequest);
}