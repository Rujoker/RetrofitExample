package com.example.sergeypchelintsev.retrofitexample;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergeypchelintsev.retrofitexample.daily.Daily;
import com.example.sergeypchelintsev.retrofitexample.daily.List;
import com.example.sergeypchelintsev.retrofitexample.daily.Weather;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherData;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;
import com.example.sergeypchelintsev.retrofitexample.views.DetailedActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sergeypchelintsev on 07.03.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public enum WeatherType {
        CITY,
        DAILY,
    }

    private Context mContext;
    private WeatherResponse mDataset;

    private ArrayList mList;
    private String mCityName = "";

    private WeatherType mType = WeatherType.CITY;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Temperature)
        TextView mTextViewTemp;
        @BindView(R.id.Time)
        TextView mTextViewTime;
        @BindView(R.id.Pressure)
        TextView mTextViewPressure;
        @BindView(R.id.Wind)
        TextView mTextViewWind;
        @BindView(R.id.Description)
        TextView mTextViewDescription;
        @BindView(R.id.icon)
        ImageView mIcon;

        private View root;

        ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }

    public MyAdapter(WeatherResponse myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    public MyAdapter(Context context) {
        this(context, new ArrayList());
    }

    public MyAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;
    }

    public void setWeatherType(@NonNull WeatherType type) {
        mType = type;
    }

    public void setList(java.util.List list) {
        if (!mList.isEmpty()) {
            mList.clear();
        }

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setCity(String cityName) {
        mCityName = cityName;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mType == WeatherType.CITY) {
            WeatherData data = mDataset.getList().get(position);
            holder.mTextViewTime.setText(data.getDtTxt());
            holder.mTextViewTemp.setText(String.valueOf(data.getMain().getTemp()));
            holder.mTextViewPressure.setText(String.valueOf(data.getMain().getPressure().toString()));
            holder.mTextViewWind.setText(String.valueOf(data.getWind().getSpeed()));
           // holder.mTextViewDescription.setText(data.getWeather().get(0).getDescription());
            Picasso.with(mContext)
                    .load("http://openweathermap.org/img/w/" + data.getWeather().get(0).getIcon() + ".png")
                    .into(holder.mIcon);
        } else if (mType == WeatherType.DAILY) {
            List data = (List) mList.get(position);

            long unixSeconds = data.getDt();
            Date date = new Date(unixSeconds*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
            String formattedDate = sdf.format(date);
            holder.mTextViewTime.setText(formattedDate);
            holder.mTextViewTemp.setText(String.valueOf(data.getTemp().getDay()));
            holder.mTextViewPressure.setText(String.valueOf(data.getPressure()));
            holder.mTextViewWind.setText(String.valueOf(data.getSpeed()));
            holder.mTextViewDescription.setText(data.getWeather().get(0).getDescription());
            holder.root.setOnClickListener(v -> onClickItem(v, data));
            Picasso.with(mContext)
                    .load("http://openweathermap.org/img/w/" + data.getWeather().get(0).getIcon() + ".png")
                    .into(holder.mIcon);
        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void onClickItem(View view, List data) {
        if (mContext != null) {
            Intent intent = new Intent(mContext, DetailedActivity.class);
            intent.putExtra(DetailedActivity.EXTERNAL_DATE, data.getDt());
            intent.putExtra(DetailedActivity.EXTERNAL_NAME, mCityName);
            mContext.startActivity(intent);

        }
    }
}


