package com.example.business_library.repository

import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.BaseException
import com.example.aac_library.base.BaseRepo
import com.example.aac_library.base.interf.RequestCallBack
import com.example.business_library.BlEConnectCallBack
import com.example.business_library.bean.*

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:28
 * @desc: 数据仓库
 */
class HomeRepository(remoteDataSource: IHomeData) : BaseRepo<IHomeData>(remoteDataSource) {

    /**
     * 获取首页文章
     */
    fun queryHomeArticleData(pageIndex: Int,isRefresh:Boolean):MutableLiveData<HomeArticalBean> {
        val mutableLiveData: MutableLiveData<HomeArticalBean> = MutableLiveData()
        remoteDataSource.queryHomeArticleData(pageIndex, object : RequestCallBack<HomeArticalBean> {

            override fun onFailed(error: BaseException) {
                throw error

            }

            override fun onSucess(homeArticalBeans: HomeArticalBean) {
                mutableLiveData.value = homeArticalBeans
            }
        },isRefresh)
        return mutableLiveData
    }

    /**
     * 获取首页广告banner
     */
    fun queryHomeBannerData(): MutableLiveData<MutableList<HomeBannerBean>> {
        val mutableLiveData: MutableLiveData<MutableList<HomeBannerBean>> = MutableLiveData()
        remoteDataSource.queryHomeBannerData(object : RequestCallBack<MutableList<HomeBannerBean>> {
            override fun onFailed(error: BaseException?) {
                error?.printStackTrace()
            }

            override fun onSucess(homeBannerBean: MutableList<HomeBannerBean>?) {
                mutableLiveData.value = homeBannerBean
            }
        })
        return mutableLiveData
    }

    /**
     * 连接蓝牙
     */
    fun connectBletooth(isAuto:Boolean): MutableLiveData<BLEState>{
        var bleLiveData = MutableLiveData<BLEState>()
        remoteDataSource.connectBLEtooth(isAuto,object : BlEConnectCallBack{
            override fun onBLEConnectSucess(bleState: BLEState) {
                bleLiveData.value = bleState
            }

            override fun onBleConnectFailed(bleState: BLEState) {
                bleLiveData.value = bleState
            }
        })
        return bleLiveData
    }

    fun getMusicInfo():MusicInfo?{
        return remoteDataSource.getMusicInfo()
    }
    fun playBTMusic(){
        remoteDataSource.playBTMusic()
    }
    fun playUSBMusic(){
        remoteDataSource.playUSBMusic()
    }
    fun pauseMusic(){
        remoteDataSource.pauseMusic()
    }
    fun playPre(){
        remoteDataSource.playPre()
    }
    fun playNext(){
        remoteDataSource.playNext()
    }

}