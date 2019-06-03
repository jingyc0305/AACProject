package com.example.aacdemo.weather;

import androidx.lifecycle.MutableLiveData;
import com.example.aacdemo.base.BaseViewModel;

public class WeatherViewModel extends BaseViewModel {

    private MutableLiveData<Weather> weatherMutableLiveData;

    private WeatherRepo weatherRepo;
    public WeatherViewModel(){
        weatherMutableLiveData = new MutableLiveData<>();
        weatherRepo = new WeatherRepo(new WeatherDataSource(this));
    }

    public void getWeather(String cityName){
        weatherRepo.queryWeather(cityName).observe(getLifecycleOwner(), weather -> weatherMutableLiveData.setValue(weather));
    }

    public MutableLiveData<Weather> getWeatherMutableLiveData() {
        return weatherMutableLiveData;
    }
}
