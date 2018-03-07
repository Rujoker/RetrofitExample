package com.example.sergeypchelintsev.retrofitexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sergeypchelintsev on 06.03.2018.
 */

public interface OpenWeatherAPI {

    @GET("/data/2.5/forecast?appid=f4540f77f79170327fc25b3039261c45")
    Call<WeatherResponse> getCity(@Query("id") String city_ID);
}