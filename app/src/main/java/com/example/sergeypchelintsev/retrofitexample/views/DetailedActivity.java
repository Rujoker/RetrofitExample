package com.example.sergeypchelintsev.retrofitexample.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.example.sergeypchelintsev.retrofitexample.MyAdapter;
import com.example.sergeypchelintsev.retrofitexample.R;
import com.example.sergeypchelintsev.retrofitexample.daily.Daily;
import com.example.sergeypchelintsev.retrofitexample.network.NetworkManager;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherData;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class DetailedActivity extends AppCompatActivity {

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;

    public static final String EXTERNAL_DATE = "date";
    public static final String EXTERNAL_NAME = "name";

    private String cityName;
    private long dayTime = 0;

    private MyAdapter mAdapter;
    private Subscription mSubscription;

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


        mSubscription = NetworkManager.getInstance().openWeatherAPI().requestDetailedWeather(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((WeatherResponse weatherResponse) -> {

                            long startDay = dayTime - 43200;
                            long endDay = startDay + 86400; //msec

                            java.util.List<WeatherData> weatherOfDay = Stream.of(weatherResponse.getList()).filter(weather -> {
                                return (weather.getDt() > startDay && weather.getDt() <= endDay);
                            }).toList();

                            weatherResponse.setList(weatherOfDay);
                            weatherResponse.setCnt(weatherOfDay.size());

                            mAdapter.setList(weatherResponse.getList());
                        }
                );
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribe();
    }

    private void unsubscribe() {
        if (mSubscription != null) mSubscription.unsubscribe();
    }

}