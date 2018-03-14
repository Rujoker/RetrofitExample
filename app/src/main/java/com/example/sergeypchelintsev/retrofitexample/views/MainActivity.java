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
import com.example.sergeypchelintsev.retrofitexample.current.Current;
import com.example.sergeypchelintsev.retrofitexample.daily.Daily;
import com.example.sergeypchelintsev.retrofitexample.network.NetworkManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.today)
    TextView today;

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    private MyAdapter mAdapter;
    private Subscription mSubscription;
    private Subscription mSubscriptionCurDay;

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

        updateWeather();
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        updateWeather();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribeAll();
    }

    private void unsubscribeAll() {
        if (mSubscription != null) mSubscription.unsubscribe();
        if (mSubscriptionCurDay != null) mSubscriptionCurDay.unsubscribe();
    }

    private void updateWeather() {
        String cityName = city.getText().toString();
        if (cityName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Введите город", Toast.LENGTH_LONG).show();
            return;
        }

        unsubscribeAll();

        mSubscriptionCurDay = NetworkManager.getInstance().openWeatherAPI().getCurrentWeather(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Current current) -> {
                    summary.setText(String.valueOf("Город: " + current.getName() +
                            ", Страна: " + current.getSys().getCountry()));
                    today.setText(String.valueOf("Сейчас: " + current.getWeather().get(0).getDescription() +
                            ", Температура: " + current.getMain().getTemp()));
                },
                        exception -> {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        });

        mSubscription = NetworkManager.getInstance().openWeatherAPI().requestDaylyWeather(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Daily daily) -> {
                    mAdapter.setList(daily.getList());
                    mAdapter.setCity(daily.getCity().getName());
                },
                        e -> {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        });
    }
}

