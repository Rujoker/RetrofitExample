
package com.example.sergeypchelintsev.retrofitexample.daily;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class List {

    @SerializedName("clouds")
    private Long mClouds;
    @SerializedName("deg")
    private Long mDeg;
    @SerializedName("dt")
    private Long mDt;
    @SerializedName("humidity")
    private Long mHumidity;
    @SerializedName("pressure")
    private Double mPressure;
    @SerializedName("snow")
    private Double mSnow;
    @SerializedName("speed")
    private Double mSpeed;
    @SerializedName("temp")
    private Temp mTemp;
    @SerializedName("weather")
    private java.util.List<Weather> mWeather;

    public Long getClouds() {
        return mClouds;
    }

    public void setClouds(Long clouds) {
        mClouds = clouds;
    }

    public Long getDeg() {
        return mDeg;
    }

    public void setDeg(Long deg) {
        mDeg = deg;
    }

    public Long getDt() {
        return mDt;
    }

    public void setDt(Long dt) {
        mDt = dt;
    }

    public Long getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Long humidity) {
        mHumidity = humidity;
    }

    public Double getPressure() {
        return mPressure;
    }

    public void setPressure(Double pressure) {
        mPressure = pressure;
    }

    public Double getSnow() {
        return mSnow;
    }

    public void setSnow(Double snow) {
        mSnow = snow;
    }

    public Double getSpeed() {
        return mSpeed;
    }

    public void setSpeed(Double speed) {
        mSpeed = speed;
    }

    public Temp getTemp() {
        return mTemp;
    }

    public void setTemp(Temp temp) {
        mTemp = temp;
    }

    public java.util.List<Weather> getWeather() {
        return mWeather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        mWeather = weather;
    }

}
