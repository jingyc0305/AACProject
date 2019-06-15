package com.example.aacdemo.weather;

import com.example.aac_library.base.interf.RequestCallBack;

public interface IWeatherData {

    void queryWeather(String cityName, RequestCallBack requestCallBack);

}
