
package com.example.sergeypchelintsev.retrofitexample.daily;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Daily {

    @SerializedName("city")
    private City mCity;
    @SerializedName("cnt")
    private Long mCnt;
    @SerializedName("cod")
    private String mCod;
    @SerializedName("list")
    private java.util.List<com.example.sergeypchelintsev.retrofitexample.daily.List> mList;
    @SerializedName("message")
    private Double mMessage;

    public City getCity() {
        return mCity;
    }

    public void setCity(City city) {
        mCity = city;
    }

    public Long getCnt() {
        return mCnt;
    }

    public void setCnt(Long cnt) {
        mCnt = cnt;
    }

    public String getCod() {
        return mCod;
    }

    public void setCod(String cod) {
        mCod = cod;
    }

    public java.util.List<com.example.sergeypchelintsev.retrofitexample.daily.List> getList() {
        return mList;
    }

    public void setList(java.util.List<com.example.sergeypchelintsev.retrofitexample.daily.List> list) {
        mList = list;
    }

    public Double getMessage() {
        return mMessage;
    }

    public void setMessage(Double message) {
        mMessage = message;
    }

}
