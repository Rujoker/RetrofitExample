package com.example.sergeypchelintsev.retrofitexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sergeypchelintsev on 06.03.2018.
 */

public interface OpenWeatherAPI {

    @GET("/data/2.5/forecast?id={city_ID}")
    Call<GithubUser> getCity(@Path("city_ID") String city_ID);
}