package com.example.aacdemo.weather;

import com.example.aacdemo.base.interf.RequestCallBack;

public interface IWeatherData {

    void queryWeather(String cityName, RequestCallBack requestCallBack);

}
