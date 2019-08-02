package com.example.business_library.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.aac_library.base.BaseViewModel
import com.example.business_library.bean.HomeArticalBean
import com.example.business_library.bean.HomeBannerBean
import com.example.business_library.repository.HomeDataSource
import com.example.business_library.repository.HomeRepository

/**
 * @author: JingYuchun
 * @date: 2019/7/1 14:26
 * @desc: 首页ViewModel
 */
class HomeViewModel: BaseViewModel() {
    private val articalLiveData: MutableLiveData<HomeArticalBean> = MutableLiveData()
    private val bannerLiveData: MutableLiveData<MutableList<HomeBannerBean>> = MutableLiveData()
    private val homeRepository = HomeRepository(HomeDataSource(this))

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

    fun getArticalLiveData(): MutableLiveData<HomeArticalBean> {
        return articalLiveData
    }

    fun getBannerLiveData(): MutableLiveData<MutableList<HomeBannerBean>> {
        return bannerLiveData
    }
}