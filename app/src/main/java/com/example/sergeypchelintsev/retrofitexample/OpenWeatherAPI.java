package com.example.sergeypchelintsev.retrofitexample;

import com.example.sergeypchelintsev.retrofitexample.current.Current;
import com.example.sergeypchelintsev.retrofitexample.daily.Daily;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sergeypchelintsev on 06.03.2018.
 */

public interface OpenWeatherAPI {

    @GET("/data/2.5/weather?lang=ru&units=metric&appid=21a8d636ae57d56ec6fb2ebb46d3e0b4")
    Observable<Current> getCurrentWeather(@Query("q") String city_name);

    @GET("/data/2.5/forecast?lang=ru&units=metric&appid=21a8d636ae57d56ec6fb2ebb46d3e0b4")
    Observable<WeatherResponse> requestDetailedWeather(@Query("q") String city_name);

    @GET("/data/2.5/forecast/daily?lang=ru&units=metric&cnt=5&appid=21a8d636ae57d56ec6fb2ebb46d3e0b4")
    Observable<Daily> requestDaylyWeather(@Query("q") String city_name);

}
