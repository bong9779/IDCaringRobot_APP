package com.example.graduateproject;
import com.example.graduateproject.JoinData;
import com.example.graduateproject.JoinResponse;
import com.example.graduateproject.LoginData;
import com.example.graduateproject.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/time")
    Call<TimeResponse> userTime(@Body TimeData data);

    @POST("/user/mednamecheck")
    Call<MednameResponse> userTime(@Body MednameData data);

    @POST("/user/delmeddata")
    Call<DelmedResponse> userTime(@Body DelmedData data);

    @POST("/user/gettime")
    Call<TimeGetResponse> userGetTime(@Body TimeGetData data);
}