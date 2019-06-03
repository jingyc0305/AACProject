package com.example.aacdemo.http.interceptor;

import android.text.TextUtils;
import com.example.aacdemo.http.HttpConfig;
import okhttp3.*;

import java.io.IOException;

public class FilterInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl.Builder builder = request.url().newBuilder();
        Headers headers = request.headers();

        if (headers != null && headers.size() > 0) {
            String requestType  = headers.get(HttpConfig.HTTP_REQUEST_TYPE_KEY);
            if(!TextUtils.isEmpty(requestType)){
                switch (requestType){
                    case HttpConfig.HTTP_REQUEST_WEATHER:
                        builder.addQueryParameter(HttpConfig.KEY,HttpConfig.KEY_WEATHER);
                        break;
                }
            }
        }
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.removeHeader(HttpConfig.HTTP_REQUEST_TYPE_KEY);
        requestBuilder.url(builder.build());
        return chain.proceed(requestBuilder.build());
    }
}
