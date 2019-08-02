package com.example.aacdemo.wanandroid.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aacdemo.R
import com.example.business_library.bean.HomeArticalBean

/**
 * @author: JingYuchun
 * @date: 2019/7/2 17:22
 * @desc: 首页文章适配器
 */
class HomeArticalAdapter(private var onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<HomeArticalAdapter.MyViewHolder>() {

    private var articals = mutableListOf<HomeArticalBean.DatasBean>()
    fun setNewData(datas:MutableList<HomeArticalBean.DatasBean>){
        this.articals.clear()
        this.articals.addAll(datas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_artical_lis,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = articals[position].title
        holder.subTitle.text = articals[position].chapterName
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
        holder.itemView.setOnLongClickListener{
            onItemClickListener.onItemLongClick(position)
        }
    }
    override fun getItemCount(): Int{
        return articals.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title:TextView = itemView.findViewById(R.id.artical_title) as TextView
        val subTitle:TextView = itemView.findViewById(R.id.artical_sub_title) as TextView


    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int):Boolean
    }
}