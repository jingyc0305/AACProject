package com.example.roomlib.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.greendaolib.MusicBean
import com.example.greendaolib.R
import com.example.greendaolib.databinding.ItemMusicBeanListBinding

/**
 * @author: JingYuchun
 * @date:
 * @desc: adapter use data binding
 */
internal class MusicBeanAdapter(private val context: Context, private var musicList: List<MusicBean>?) :
    RecyclerView.Adapter<MusicBeanAdapter.MusicViewHoder>() {

    fun setMuiscList(wordList: List<MusicBean>) {
        this.musicList = wordList
        if (wordList.isNotEmpty()) {
            notifyItemInserted(wordList.size)
        } else {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHoder {
        val inflate = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemMusicBeanListBinding>(inflate, R.layout.item_music_bean_list, parent, false)
        return MusicViewHoder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHoder, position: Int) {
        holder.binding.music = musicList!![position]
    }

    override fun getItemCount(): Int {
        return if (musicList == null) {
            0
        } else musicList!!.size
    }

    internal inner class MusicViewHoder(var binding: ItemMusicBeanListBinding) : RecyclerView.ViewHolder(binding.root)
}
