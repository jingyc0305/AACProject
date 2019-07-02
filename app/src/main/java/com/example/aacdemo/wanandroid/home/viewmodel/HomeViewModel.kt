package com.example.aacdemo.wanandroid.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.aac_library.base.BaseViewModel
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean
import com.example.aacdemo.wanandroid.home.bean.HomeBannerBean
import com.example.aacdemo.wanandroid.home.repository.HomeDataSource
import com.example.aacdemo.wanandroid.home.repository.HomeRepository

/**
 * @author: JingYuchun
 * @date: 2019/7/1 14:26
 * @desc: 首页ViewModel
 */
class HomeViewModel : BaseViewModel() {

    private val articalLiveData: MutableLiveData<HomeArticalBean> = MutableLiveData()
    private val bannerLiveData: MutableLiveData<HomeBannerBean> = MutableLiveData()
    private val homeRepository: HomeRepository = HomeRepository(HomeDataSource(this))

    /**
     * 获取首页文章
     */
    fun getHomeArticalData(pageIndex: Int) {
        homeRepository?.queryHomeArticleData(pageIndex).observe(lifecycleOwner!!, Observer {
            articalLiveData.value = it
        })
    }

    /**
     * 获取首页Banner
     */
    fun getHomeBannerData() {
        homeRepository?.queryHomeBannerData().observe(lifecycleOwner!!, Observer {
            bannerLiveData.value = it
        })

    }

    fun getArticalLiveData(): MutableLiveData<HomeArticalBean> {
        return articalLiveData
    }

    fun getBannerLiveData(): MutableLiveData<HomeBannerBean> {
        return bannerLiveData
    }
}