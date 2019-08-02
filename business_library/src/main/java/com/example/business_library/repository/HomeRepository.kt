package com.example.business_library.repository

import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.BaseException
import com.example.aac_library.base.BaseRepo
import com.example.aac_library.base.interf.RequestCallBack
import com.example.business_library.bean.HomeArticalBean
import com.example.business_library.bean.HomeBannerBean

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

}