package com.example.sergeypchelintsev.retrofitexample.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.example.sergeypchelintsev.retrofitexample.MyAdapter;
import com.example.sergeypchelintsev.retrofitexample.OpenWeatherAPI;
import com.example.sergeypchelintsev.retrofitexample.R;
import com.example.sergeypchelintsev.retrofitexample.network.NetworkManager;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherData;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailedActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;


    public static final String EXTERNAL_DATE = "date";
    public static final String EXTERNAL_NAME = "name";

    private RecyclerView.LayoutManager mLayoutManager;

    private String cityName;
    private long dayTime = 0;

    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this);
        mAdapter.setWeatherType(MyAdapter.WeatherType.CITY);
        mRecyclerView.setAdapter(mAdapter);

        dayTime = getIntent().getLongExtra(EXTERNAL_DATE, 0);
        cityName = getIntent().getStringExtra(EXTERNAL_NAME);


        NetworkManager.getInstance().openWeatherAPI().getCity(cityName)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            WeatherResponse result = response.body();
                            WeatherResponse newResult = result;

                            long startDay = dayTime - 43200;
                            long endDay = startDay + 86400; //msec

                            java.util.List<WeatherData> weatherOfDay = Stream.of(result.getList()).filter(weather -> {
                                return (weather.getDt() > startDay && weather.getDt() <= endDay);
                            }).toList();

                            newResult.setList(weatherOfDay);
                            newResult.setCnt(weatherOfDay.size());




                            mAdapter.setList(result.getList());


                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Toast.makeText(DetailedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}