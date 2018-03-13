
package com.example.sergeypchelintsev.retrofitexample.perThree;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class WeatherResponse {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<WeatherData> list = null;
    @SerializedName("city")
    @Expose
    private City city;

    public String getCod() {
        return cod;
    }

    public Double getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    @NonNull
    public List<WeatherData> getList() {
        return list == null ? Collections.emptyList() : list;
    }

    @NonNull
    public void setList(List<WeatherData> list) {
        if (list != null) this.list = list;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }


    public City getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                ", city=" + city +
                '}';
    }
}
