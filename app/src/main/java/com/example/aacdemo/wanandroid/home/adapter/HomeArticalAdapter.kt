package com.example.aacdemo.wanandroid.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aacdemo.R
import com.example.aacdemo.wanandroid.home.bean.HomeArticalBean

/**
 * @author: JingYuchun
 * @date: 2019/7/2 17:22
 * @desc: 首页文章适配器
 */
class HomeArticalAdapter(private val articals:MutableList<HomeArticalBean.DatasBean>) : RecyclerView.Adapter<HomeArticalAdapter.MyViewHodel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHodel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_artical_lis,parent,false)
        return MyViewHodel(view)
    }

    override fun onBindViewHolder(holder: MyViewHodel, position: Int) {
        holder.title.text = articals[position].title
        holder.subTitle.text = articals[position].chapterName
    }
    override fun getItemCount() = articals.size

    class MyViewHodel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title:TextView = itemView.findViewById(R.id.artical_title)
        val subTitle:TextView = itemView.findViewById(R.id.artocal_sub_title)

    }
}