package com.example.aacdemo.wanandroid.home.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aac_library.base.view.BaseFragment
import com.example.aacdemo.R
import com.example.aacdemo.wanandroid.home.adapter.HomeArticalAdapter
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean
import com.example.aacdemo.wanandroid.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.frag_home.*

/**
 * @author: JingYuchun
 * @date: 2019/6/27 15:04
 * @desc: 首页
 */
class HomeFragment :BaseFragment(){
    private var homeViewModel:HomeViewModel? = null
    private var adapter: HomeArticalAdapter? = null
    private var articalList : ArrayList<HomeArticalBean>? = ArrayList()
    override fun initViewModel(): ViewModel? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel?.lifecycleOwner = this
        homeViewModel?.getArticalLiveData()?.observe(this, Observer {
            home_artical_rv.adapter = adapter
            adapter?.notifyDataSetChanged()
        })
        return homeViewModel
    }

    override fun initLayoutResId(): Int {
        return R.layout.frag_home
    }

    @SuppressLint("WrongConstant")
    override fun initView() {
        adapter = HomeArticalAdapter(articalList!!)
        val manager = LinearLayoutManager(context)
        home_artical_rv.let {
            manager.orientation = LinearLayoutManager.VERTICAL
            it?.layoutManager = manager
            it?.adapter = adapter
        }


    }
    override fun initData() {
        homeViewModel?.getHomeArticalData(0)
        homeViewModel?.getBannerLiveData()
    }

    override fun initDataBinding() {
    }
}