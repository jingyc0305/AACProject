package com.example.business_library.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.aac_library.base.BaseViewModel
import com.example.business_library.bean.*
import com.example.business_library.repository.HomeDataSource
import com.example.business_library.repository.HomeRepository
import com.example.business_library.repository.IHomeData

/**
 * @author: JingYuchun
 * @date: 2019/7/1 14:26
 * @desc: 首页ViewModel
 */
class HomeViewModel: BaseViewModel() {
    var currMusicState:MusicState? = null
    var articalLiveData: MutableLiveData<HomeArticalBean> = MutableLiveData()
    var bannerLiveData: MutableLiveData<MutableList<HomeBannerBean>> = MutableLiveData()
    //蓝牙状态
    var blelLiveData : MutableLiveData<BLEState> =  MutableLiveData()


    var homeRepository = HomeRepository(HomeDataSource(this))

    /**
     * 获取首页文章
     */
    fun getHomeArticalData(pageIndex: Int,isRefresh:Boolean) {
        homeRepository.queryHomeArticleData(pageIndex,isRefresh).observe(lifecycleOwner!!, Observer {
            articalLiveData.value = it
        })
    }

    /**
     * 获取首页Banner
     */
    fun getHomeBannerData() {
        homeRepository.queryHomeBannerData().observe(lifecycleOwner!!, Observer {
            bannerLiveData.value = it
        })

    }

    fun connectBletooth(isAuto:Boolean){
        homeRepository.connectBletooth(isAuto).observe(lifecycleOwner!!, Observer {
            blelLiveData.value  = it
        })
    }

    fun getMusicState(): MusicState? {
        return currMusicState
    }
    fun setMusicState(musicState: MusicState){
        this.currMusicState = musicState
    }
    fun getMusicInfo(): MusicInfo?{
        return homeRepository.getMusicInfo()
    }
    fun playBTMusic(){
        homeRepository.playBTMusic()
    }
    fun playUSBMusic(){
        homeRepository.playUSBMusic()
    }
    fun pauseMusic(){
        homeRepository.pauseMusic()
    }
    fun playPre(){
        homeRepository.playPre()
    }
    fun playNext(){
        homeRepository.playNext()
    }
}