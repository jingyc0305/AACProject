package com.example.aacdemo.http;

import com.example.aacdemo.base.BaseResponse;
import com.example.aacdemo.weather.Weather;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":"+HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponse<Weather>> getWeather(@Query("cityname") String cityName);
}
