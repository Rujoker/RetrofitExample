package com.example.sergeypchelintsev.retrofitexample;

import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sergeypchelintsev on 06.03.2018.
 */

public interface OpenWeatherAPI {
    @GET("/data/2.5/forecast?lang=ru&units=metric&appid=21a8d636ae57d56ec6fb2ebb46d3e0b4")
    Call<WeatherResponse> getCity(@Query("id") String city_ID);
}