
package com.android.m4racz.stormy.ForecastWeather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastWeather {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("list")
    @Expose
    private java.util.List<com.android.m4racz.stormy.ForecastWeather.List> list = null;
    @SerializedName("message")
    @Expose
    private Double message;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public java.util.List<com.android.m4racz.stormy.ForecastWeather.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.android.m4racz.stormy.ForecastWeather.List> list) {
        this.list = list;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

}
