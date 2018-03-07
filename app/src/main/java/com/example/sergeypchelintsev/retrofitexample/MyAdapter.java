package com.example.sergeypchelintsev.retrofitexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sergeypchelintsev on 07.03.2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private WeatherResponse mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.Temperature) TextView mTextViewTemp;
        @BindView(R.id.Time) TextView mTextViewTime;
        @BindView(R.id.Pressure) TextView mTextViewPressure;
        @BindView(R.id.Wind) TextView mTextViewWind;
        @BindView(R.id.Description) TextView mTextViewDescription;
        @BindView(R.id.icon) ImageView mIcon;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public MyAdapter(WeatherResponse myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List data = mDataset.getList().get(position);
        holder.mTextViewTime.setText(data.getDtTxt());
        holder.mTextViewTemp.setText(String.valueOf(data.getMain().getTemp()));
        holder.mTextViewPressure.setText(String.valueOf(data.getMain().getPressure().toString()));
        holder.mTextViewWind.setText(String.valueOf(data.getWind().getSpeed()));
        holder.mTextViewDescription.setText(data.getWeather().get(0).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.getCnt();
    }
}


