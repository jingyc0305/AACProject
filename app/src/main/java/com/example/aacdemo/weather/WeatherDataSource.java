package com.example.aacdemo.weather;

import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aacdemo.api.ApiService;
import com.example.aac_library.base.BaseRemoteDataSource;

public class WeatherDataSource extends BaseRemoteDataSource implements IWeatherData{
    public WeatherDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void queryWeather(String cityName, RequestCallBack requestCallBack) {

        execute(getService(ApiService.class).getWeather(cityName),requestCallBack);
    }
}
