package com.example.havastest.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

   private static Retrofit retrofit=null;
   public static Retrofit getClient(String BaseURL){
       if(retrofit==null){
           retrofit= new Retrofit.Builder().
                   baseUrl(BaseURL).
                   addConverterFactory(GsonConverterFactory.create()).
                   build();
       }
       return retrofit;
   }
}
