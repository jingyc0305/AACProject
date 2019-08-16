package com.example.business_library.repository

import com.example.aac_library.base.BaseRemoteDataSource
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.interf.RequestCallBack
import com.example.aacdemo.api.WanAndroidService
import com.example.business_library.BlEConnectCallBack
import com.example.business_library.api.HttpConfig
import com.example.business_library.bean.BLEState
import com.example.business_library.bean.MusicInfo
import com.example.business_library.bean.MusicState

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:28
 * @desc: 获取数据源
 */
class HomeDataSource(baseViewModel: BaseViewModel) : BaseRemoteDataSource(baseViewModel), IHomeData {

    override fun playBTMusic() {

    }

    override fun playUSBMusic() {
    }

    override fun pauseMusic() {
    }

    override fun playNext() {
    }

    override fun playPre() {
    }

    override fun getMusicInfo(): MusicInfo? {
        return MusicInfo("","Take Me To Your Heart","Michael","","Hiding from rain and snow ,trying to forget but I won't let go")
    }
    override fun <T> queryHomeArticleData(pageIndex:Int,requestCallBack: RequestCallBack<T>, isRefresh : Boolean) {
        execute(getService(WanAndroidService::class.java, HttpConfig.wanAndroidBaseUrl).getHomeArticalData(pageIndex),requestCallBack,isRefresh)
    }

    override fun <T> queryHomeBannerData(requestCallBack: RequestCallBack<T>) {
        execute(getService(WanAndroidService::class.java,HttpConfig.wanAndroidBaseUrl).getHomeBanner(),requestCallBack,false)
    }
    override fun connectBLEtooth(isAuto:Boolean,isblEConnectCallBack: BlEConnectCallBack) {
        // 连接蓝牙
        // 连接成功
        var bleState = BLEState(true,"BE:SE:0F:JU:F8")

        isblEConnectCallBack.onBLEConnectSucess(bleState)
    }

}