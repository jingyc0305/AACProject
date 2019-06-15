package com.example.aacdemo.api;

import com.example.aac_library.base.BaseResponse;
import com.example.aac_library.http.HttpConfig;
import com.example.aacdemo.qrcode.QrCode;
import com.example.aacdemo.weather.Weather;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * 接口请求服务
 */
public interface ApiService {

    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":"+HttpConfig.HTTP_REQUEST_WEATHER})
    @GET("onebox/weather/query")
    Observable<BaseResponse<Weather>> getWeather(@Query("cityname") String cityName);

    /**
     * 生成二维码
     */
    @Headers({HttpConfig.HTTP_REQUEST_TYPE_KEY + ":" + HttpConfig.HTTP_REQUEST_QR_CODE})
    @GET("qrcode/api")
    Observable<BaseResponse<QrCode>> createQrCode(@Query("text") String text, @Query("width") int width );
}
