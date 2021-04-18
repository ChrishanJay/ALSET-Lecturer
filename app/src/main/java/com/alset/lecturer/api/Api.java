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
    Call<LoginResponse> setPassword(@Body PasswordRequest passwordRequest);

}