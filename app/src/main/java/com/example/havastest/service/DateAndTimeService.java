package com.example.havastest.service;

import com.example.havastest.model.DateDataClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DateAndTimeService {
    @GET("/")
    Call<DateDataClass> getDate();
}
