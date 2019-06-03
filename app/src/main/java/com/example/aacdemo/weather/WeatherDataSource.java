package com.example.aacdemo.weather;

import com.example.aacdemo.base.BaseViewModel;
import com.example.aacdemo.base.interf.RequestCallBack;
import com.example.aacdemo.http.ApiService;
import com.example.aacdemo.http.BaseRemoteDataSource;

public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherData{
    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallBack requestCallBack) {

        execute(getService(ApiService.class).getWeather(cityName),requestCallBack);
    }
}
