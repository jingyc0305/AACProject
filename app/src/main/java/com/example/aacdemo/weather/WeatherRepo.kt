package com.example.aacdemo.weather

import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.BaseException
import com.example.aac_library.base.BaseRepo
import com.example.aac_library.base.interf.RequestCallBack

class WeatherRepo(remoteDataSource: IWeatherData) : BaseRepo<IWeatherData>(remoteDataSource) {

    fun queryWeather(cityName: String): MutableLiveData<Weather> {
        val mutableLiveData = MutableLiveData<Weather>()

        remoteDataSource.queryWeather(cityName, object : RequestCallBack<Weather> {

            override fun onSucess(weather: Weather) {
                mutableLiveData.value = weather
            }

            override fun onFailed(error: BaseException) {
                error.printStackTrace()
            }
        })
        return mutableLiveData
    }
}
