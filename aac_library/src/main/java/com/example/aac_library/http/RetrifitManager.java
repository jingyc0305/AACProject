package com.example.aac_library.http;

import com.example.aac_library.BuildConfig;
import com.example.aac_library.base.BaseApp;
import com.example.aac_library.base.BaseResponse;
import com.example.aac_library.base.WanBaseResponse;
import com.example.aac_library.http.execption.ParamterInvalidException;
import com.example.aac_library.http.execption.ServerErrorException;
import com.example.aac_library.http.execption.TimeOutException;
import com.example.aac_library.http.execption.TokenInvalidException;
import com.example.aac_library.http.interceptor.CacheInterceptor;
import com.example.aac_library.http.interceptor.FilterInterceptor;
import com.example.aac_library.http.interceptor.OfflineCacheInterceptor;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RetrifitManager {

    private static final long READ_TIMEOUT = 10;
    private static final long WRITE_TIMEOUT = 10;
    private static final long CONNECT_TIMEOUT = 10;
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private OkHttpClient.Builder builder;
    private OkHttpClient okHttpClient;

    public RetrifitManager() {

    }

    private static RetrifitManager instance;

    public static RetrifitManager getInstance() {
        if (instance == null) {
            synchronized (RetrifitManager.class) {
                if (instance == null) {
                    instance = new RetrifitManager();
                }
            }
        }
        return instance;
    }

    private Retrofit getRetrifit(String baseUrl) {

        //定义缓存路径
        File cacheFile = new File(BaseApp.getAppContext().getExternalCacheDir().toString(),"cache");
        //缓存大小为 10MB
        int cacheSize = 10 * 1024 * 1024;
        //初始化缓存对象
        Cache cache = new Cache(cacheFile,cacheSize);
        //构建OKHttpClient
        builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(new CacheInterceptor())
                .addInterceptor(new OfflineCacheInterceptor())
                .addInterceptor(new FilterInterceptor())
                .cache(cache)
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        okHttpClient = builder.build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <T> T getService(Class<T> clz){
        return getService(clz,HttpConfig.BASE_URL_WEATHER);
    }

    public <T> T getService(Class<T> clz, String baseUrl) {
        T value;
        if (serviceMap.containsKey(baseUrl)) {
            Object obj = serviceMap.get(baseUrl);
            if (obj == null) {
                value = getRetrifit(baseUrl).create(clz);
                serviceMap.put(baseUrl, value);
            } else {
                value = (T) obj;
            }
        } else {
            value = getRetrifit(baseUrl).create(clz);
            serviceMap.put(baseUrl, value);
        }
        return value;

    }

    public <T> ObservableTransformer<WanBaseResponse<T>, T> applySchedulers() {
        return observble -> {
            try {
                return (ObservableSource<T>) observble.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<WanBaseResponse<T>, ObservableSource<?>>) result -> {
                            switch (result.getCode()) {
                                case HttpCode.SUCCESS: {
                                    return RetrifitManager.this.createData(result.getData());
                                }
                                case HttpCode.TOKEN_INVALID: {
                                    throw new TokenInvalidException();
                                }
                                case HttpCode.NETWORK_TIME_OUT: {
                                    throw new TimeOutException();
                                }
                                case HttpCode.PARAMETER_INVALID: {
                                    throw new ParamterInvalidException();
                                }
                                default: {
                                    throw new ServerErrorException();
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private <T> ObservableSource<T> createData(final T data) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(data);
                emitter.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        });

    }


}
