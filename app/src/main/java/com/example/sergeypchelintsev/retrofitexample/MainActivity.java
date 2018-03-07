package com.example.sergeypchelintsev.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.summary)
    TextView summary;
    @BindView(R.id.today)
    TextView today;

    private String BASE_URL = "http://samples.openweathermap.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

    @OnClick (R.id.button)
    public void onClick(View view) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenWeatherAPI service = client.create(OpenWeatherAPI.class);
        Call<WeatherResponse> call = service.getCity(city.getText().toString());

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    WeatherResponse result = response.body();

                    String sum;
                    String str;
                    str = "";
                    sum = "City: " + result.getCity().getName() + ", Country: " + result.getCity().getCountry();
                    for (int i = 0; i < 1; i++) {
                        str = "Time: " + result.getList().get(i).getDtTxt() +
                                ", Description: " + result.getList().get(i).getWeather().get(0).getDescription() +
                                ", Temp: " + result.getList().get(i).getMain().getTemp() +
                                ", Maximal: " + result.getList().get(i).getMain().getTempMax() +
                                ", Minimal: " + result.getList().get(i).getMain().getTempMin() +
                                ", Pressure: " + result.getList().get(i).getMain().getPressure();
                    }

                    summary.setText(sum);
                    today.setText(str);

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }


            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

