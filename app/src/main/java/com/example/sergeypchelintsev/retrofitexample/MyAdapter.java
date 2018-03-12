package com.example.sergeypchelintsev.retrofitexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherData;
import com.example.sergeypchelintsev.retrofitexample.perThree.WeatherResponse;
import com.example.sergeypchelintsev.retrofitexample.views.DetailedActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sergeypchelintsev on 07.03.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context mContext;
    private WeatherResponse mDataset;

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

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        WeatherData data = mDataset.getList().get(position);
        holder.mTextViewTime.setText(data.getDtTxt());
        holder.mTextViewTemp.setText(String.valueOf(data.getMain().getTemp()));
        holder.mTextViewPressure.setText(String.valueOf(data.getMain().getPressure().toString()));
        holder.mTextViewWind.setText(String.valueOf(data.getWind().getSpeed()));
        holder.mTextViewDescription.setText(data.getWeather().get(0).getDescription());

        holder.root.setOnClickListener(v-> onClickItem(v, data));
        Picasso.with(mContext)
                .load("http://openweathermap.org/img/w/" + data.getWeather().get(0).getIcon() + ".png")
                .into(holder.mIcon);

    }


    @Override
    public int getItemCount() {
        return mDataset.getCnt();
    }

    private void onClickItem(View view, WeatherData weatherData) {
//        List<WeatherData> weatherOfDay = Stream.of(mDataset.getList()).filter(weather -> {
//            return weather.getDt() >= startDay && weather.getDt() <= endDay;
//        }).toList();

        if (mContext != null) {
            Intent intent = new Intent(mContext, DetailedActivity.class);
            intent.putExtra(DetailedActivity.EXTERNAL_DATE, weatherData.getDt());
            mContext.startActivity(intent);



        }
    }
}


