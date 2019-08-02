package com.example.aac_library.http.interceptor;

import com.example.aac_library.http.updownload.ProgressCallback;
import com.example.aac_library.http.updownload.ProgressResponseBody;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author: JingYuchun
 * @date: 2019/7/30 20:15
 * @desc: 上传下载进度拦截
 */
public class ProgressInterceptor implements Interceptor {
    private ProgressCallback progressCallback;

    public ProgressInterceptor(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截
        Response originalResponse = chain.proceed(chain.request());
        //包装响应体并返回
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressCallback))
                .build();
    }
}
