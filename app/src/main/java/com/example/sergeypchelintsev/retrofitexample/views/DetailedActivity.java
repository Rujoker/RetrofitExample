package com.example.sergeypchelintsev.retrofitexample.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.example.sergeypchelintsev.retrofitexample.MyAdapter;
import com.example.sergeypchelintsev.retrofitexample.OpenWeatherAPI;
import com.example.sergeypchelintsev.retrofitexample.R;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherData;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailedActivity extends AppCompatActivity {

    public static final String EXTERNAL_DATE = "date";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String BASE_URL = "http://api.openweathermap.org";

    String cityId = "524901";
    private int dayTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

//        long endDay = TimeUnit.DAYS.toMillis(1);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        dayTime = getIntent().getIntExtra(EXTERNAL_DATE, 0);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherAPI service = client.create(OpenWeatherAPI.class);
        Call<WeatherResponse> call = service.getCity(cityId);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Toast.makeText(DetailedActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    WeatherResponse result = response.body();

                    int startDay = Integer.valueOf(dayTime) - 4320000;
                    int endDay = startDay + 8640000; //msec



                    java.util.List<WeatherData> weatherOfDay = Stream.of(result.getList()).filter(weather -> {

                        return weather.getDt() >= startDay && weather.getDt() <= endDay;
                    }).toList();

                    mAdapter = new MyAdapter(result, DetailedActivity.this);
                    mRecyclerView.setAdapter(mAdapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                Toast.makeText(DetailedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
