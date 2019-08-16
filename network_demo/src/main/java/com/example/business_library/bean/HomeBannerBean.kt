package com.example.business_library.bean


/**
 * @author: JingYuchun
 * @date: 2019/7/25 11:14
 * @desc: HomeBannerBean
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

