package com.example.business_library.repository

import com.example.aac_library.base.BaseRemoteDataSource
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.interf.RequestCallBack
import com.example.aacdemo.api.WanAndroidService
import com.example.business_library.api.HttpConfig

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:28
 * @desc: 获取数据源
 */
class HomeDataSource(baseViewModel: BaseViewModel) : BaseRemoteDataSource(baseViewModel), IHomeData {

    override fun <T> queryHomeArticleData(pageIndex:Int,requestCallBack: RequestCallBack<T>, isRefresh : Boolean) {
        execute(getService(WanAndroidService::class.java, HttpConfig.wanAndroidBaseUrl).getHomeArticalData(pageIndex),requestCallBack,isRefresh)
    }

    override fun <T> queryHomeBannerData(requestCallBack: RequestCallBack<T>) {
        execute(getService(WanAndroidService::class.java,HttpConfig.wanAndroidBaseUrl).getHomeBanner(),requestCallBack,false)
    }
}