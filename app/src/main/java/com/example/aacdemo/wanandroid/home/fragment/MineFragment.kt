package com.example.aacdemo.wanandroid.home.fragment

import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.view.BaseFragment
import com.example.aacdemo.R

/**
 * @author: JingYuchun
 * @date: 2019/6/27 15:04
 * @desc:
 */
class MineFragment :BaseFragment(){
    override fun initViewModel(): BaseViewModel? {
        return null
    }

    override fun initLayoutResId(): Int {
        return R.layout.frag_mine
    }

    override fun initView() {
    }
    override fun initData() {
        startLoading()
        android.os.Handler().postDelayed({
            dismissLoading()
        }, 2000)
    }

    override fun initDataBinding() {
    }
}