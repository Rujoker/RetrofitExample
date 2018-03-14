package com.example.sergeypchelintsev.retrofitexample.network;

import com.example.sergeypchelintsev.retrofitexample.OpenWeatherAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by sergeypchelintsev on 13.03.2018.
 */

public class NetworkManager {

    private static final NetworkManager ourInstance = new NetworkManager();
    private static final String BASE_URL = "http://api.openweathermap.org";

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private Retrofit client;


    private NetworkManager() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
    }

    public OpenWeatherAPI openWeatherAPI() {
        return client.create(OpenWeatherAPI.class);
    }
}
