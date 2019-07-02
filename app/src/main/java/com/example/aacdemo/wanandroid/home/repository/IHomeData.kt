package com.example.aacdemo.wanandroid.home.repository

import com.example.aac_library.base.interf.RequestCallBack


/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:01
 * @desc: 查询首页数据
 */
interface IHomeData {
    /**
     * 查询首页文字列表
     */
    fun <T : Any?> queryHomeArticleData(pageIndex:Int,requestCallBack: RequestCallBack<T>)

    /**
     * 查询首页banner数据
     */
    fun <T : Any?> queryHomeBannerData(requestCallBack: RequestCallBack<T>)
}