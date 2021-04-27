package com.alset.lecturer.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "https://d6otawvpj7.execute-api.us-east-1.amazonaws.com/dev/";

    @POST("signin-cognito")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("newpwreq-cognito")
    Call<PasswordChangeResponse> setPassword(@Body PasswordRequest passwordRequest);

    @POST("add-class")
    Call<AddClassResponse> addClass(@Body AddClassRequest addClassRequest);

    @GET("get-courses")
    Call<List<CourseResponse>> getCourses();

    @GET("get-modules")
    Call<List<ModuleResponse>> getModules();

    @GET("get-lecturers")
    Call<List<LecturerResponse>> getLecturers();

    @GET("get-classes")
    Call<List<ClassesResponse>> getClasses();

    @GET("get-students")
    Call<List<StudentsResponse>> getStudents();

    @GET("get-attendance")
    Call<List<AttendanceResponse>> getAttendance();
}