package com.example.aacdemo.api

import com.example.aac_library.base.WanBaseResponse
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:36
 * @desc: 玩安卓Api
 */
interface WanAndroidService {

    @GET("/banner/json")
    fun getHomeBanner():Observable<WanBaseResponse<HomeArticalBean>>

    @GET("/article/list/{pageIndex}/json")
    fun getHomeArticalData(@Path("pageIndex") pageIndex:Int):Observable<WanBaseResponse<HomeArticalBean>>
}