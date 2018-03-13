package com.example.sergeypchelintsev.retrofitexample.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergeypchelintsev.retrofitexample.MyAdapter;
import com.example.sergeypchelintsev.retrofitexample.R;
import com.example.sergeypchelintsev.retrofitexample.daily.Daily;
import com.example.sergeypchelintsev.retrofitexample.network.NetworkManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button) Button button;
    @BindView(R.id.city) EditText city;
    @BindView(R.id.summary) TextView summary;
    @BindView(R.id.today) TextView today;

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;

    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this);
        mAdapter.setWeatherType(MyAdapter.WeatherType.DAILY);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        String cityName = city.getText().toString();
        NetworkManager.getInstance().openWeatherAPI().requestDaylyWeather(cityName)
                .enqueue(new Callback<Daily>() {
                    @Override
                    public void onResponse(Call<Daily> call, Response<Daily> response) {
                        if (response.isSuccessful()) {
                            Daily result = response.body();
                            summary.setText(String.valueOf("Город: " + result.getCity().getName() +
                                    ", Страна: " + result.getCity().getCountry()));
                            today.setText(String.valueOf("Сейчас: " + result.getList().get(0).getWeather().get(0).getDescription() +
                                    ", Температура: " + result.getList().get(0).getTemp().getDay()));

                            mAdapter.setList(result.getList());
                            mAdapter.setCity(result.getCity().getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<Daily> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

