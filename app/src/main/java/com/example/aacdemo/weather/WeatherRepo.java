package com.example.aacdemo.weather;

import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseRepo;
import com.example.aac_library.base.interf.RequestCallBack;

public class WeatherRepo extends BaseRepo<IWeatherData>{

    public WeatherRepo(IWeatherData remoteDataSource) {
        super(remoteDataSource);
    }

    public MutableLiveData<Weather> queryWeather(String cityName){
        MutableLiveData<Weather> mutableLiveData = new MutableLiveData<>();

        remoteDataSource.queryWeather(cityName, new RequestCallBack<Weather>() {

            @Override
            public void onSucess(Weather weather) {
                mutableLiveData.setValue(weather);
            }

            @Override
            public void onFailed(BaseException error) {
                error.printStackTrace();
            }
        });
        return mutableLiveData;
    }
}
