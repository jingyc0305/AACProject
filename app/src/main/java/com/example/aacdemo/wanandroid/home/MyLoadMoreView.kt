package com.example.aacdemo.wanandroid.home

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.example.aacdemo.R

/**
 * @author: JingYuchun
 * @date: 2019/8/2 15:11
 * @desc:
 */
class MyLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.loading_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }
}