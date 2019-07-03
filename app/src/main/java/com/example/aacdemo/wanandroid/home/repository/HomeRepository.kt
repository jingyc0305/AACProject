package com.example.aacdemo.wanandroid.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.aac_library.base.BaseException
import com.example.aac_library.base.BaseRepo
import com.example.aac_library.base.interf.RequestCallBack
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean
import com.example.aacdemo.wanandroid.home.bean.HomeBannerBean

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:28
 * @desc: 数据仓库
 */
class HomeRepository(remoteDataSource: IHomeData) : BaseRepo<IHomeData>(remoteDataSource) {

    /**
     * 获取首页文章
     */
    fun queryHomeArticleData(pageIndex: Int):MutableLiveData<HomeArticalBean> {
        val mutableLiveData: MutableLiveData<HomeArticalBean> = MutableLiveData()
        remoteDataSource.queryHomeArticleData(pageIndex, object : RequestCallBack<HomeArticalBean> {
            override fun onFailed(error: BaseException?) {

            }

            override fun onSucess(homeArticalBean: HomeArticalBean?) {
                mutableLiveData.value = homeArticalBean
            }
        })
        return mutableLiveData
    }

    /**
     * 获取首页广告banner
     */
    fun queryHomeBannerData():MutableLiveData<HomeBannerBean> {
        val mutableLiveData: MutableLiveData<HomeBannerBean> = MutableLiveData()
        remoteDataSource.queryHomeBannerData(object : RequestCallBack<HomeBannerBean> {
            override fun onFailed(error: BaseException?) {
                error?.printStackTrace()
            }

            override fun onSucess(homeBannerBean: HomeBannerBean?) {
                mutableLiveData.value = homeBannerBean
            }
        })
        return mutableLiveData
    }

}