package com.example.aacdemo.wanandroid.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.aacdemo.R
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean

/**
 * @author: JingYuchun
 * @date: 2019/7/2 17:22
 * @desc: 首页文章适配器
 */
class HomeArticalQuickAdapter() : BaseQuickAdapter<HomeArticalBean.DatasBean,BaseViewHolder>(R.layout.item_home_artical_lis) {
    override fun convert(helper: BaseViewHolder, item: HomeArticalBean.DatasBean?) {
        helper.setText(R.id.artical_title,item?.title)
        helper.setText(R.id.artical_sub_title,item?.chapterName)
    }
}