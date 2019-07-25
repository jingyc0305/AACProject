package com.example.aac_library.http.interceptor

import com.sunchen.netbus.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 *有网络时候缓存拦截器
 */
class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originResponse = chain.proceed(chain.request())

        //设置缓存时间60s 移除缓存消息头pragma
        return originResponse
            .newBuilder()
            .removeHeader("pragma")
            .header("Cache-Control", "public max-age=60")
            .build()
    }

}

/**
 * 无网络时候的缓存拦截器
 */
class OfflineCacheInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

        var  request = chain.request()
        if(!NetworkUtils.isNetworkAvailable()){
            request = request
                .newBuilder()
                .header("Cache-Control","public,only-if-cached,max-stale=60")
                .build()
        }
        return chain.proceed(request)
    }

}
