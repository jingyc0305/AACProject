package com.example.aac_library.stateview

import android.view.View

/**
 * @author: JingYuchun
 * @date: 2019/7/24 19:49
 * @desc:
 */
interface StatusClickListener {
    /**
     * 空数据布局点击方法
     * @param view 被点击的view
     */
    fun onEmptyClick(view: View)

    /**
     * 错误数据布局点击方法
     * @param view 被点击的view
     */
    fun onErrorClick(view: View)
}