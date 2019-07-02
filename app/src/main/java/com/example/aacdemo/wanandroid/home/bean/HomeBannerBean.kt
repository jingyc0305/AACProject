package com.example.aacdemo.wanandroid.home.bean

/**
 * @author: JingYuchun
 * @date: 2019/7/2 14:38
 * @desc: 首页Banner
 */
data class HomeBannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)