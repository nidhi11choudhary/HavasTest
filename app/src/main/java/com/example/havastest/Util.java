package com.example.havastest;

import com.example.havastest.service.DateAndTimeService;
import com.example.havastest.service.RetrofitClient;

public class Util {
    private static final String BASE_URL = "https://dateandtimeasjson.appspot.com";

        public static DateAndTimeService getService(){
            return RetrofitClient.getClient(BASE_URL).create(DateAndTimeService.class);
        }

}
