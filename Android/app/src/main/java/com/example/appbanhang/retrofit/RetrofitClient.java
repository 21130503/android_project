package com.example.appbanhang.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private  static Retrofit instance;
    public static  Retrofit getInstance(String baseURL){
        if(instance == null){
           instance = new Retrofit.Builder()
                   .baseUrl(baseURL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                   .build();

        }
        return  instance;
    }
}
