package com.example.roomlib.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlib.R
import com.example.roomlib.databinding.ItemMusicListBinding
import com.example.roomlib.db.Music

/**
 * @author: JingYuchun
 * @date:
 * @desc: adapter use data binding
 */
internal class MusicAdapter(private val context: Context, private var musicList: List<Music>?) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHoder>() {

    fun setMuiscList(wordList: List<Music>) {
        this.musicList = wordList
        if (wordList.isNotEmpty()){
            notifyItemInserted(wordList.size)
        }else {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHoder {
        val inflate = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<ItemMusicListBinding>(inflate, R.layout.item_music_list, parent, false)
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

    internal inner class MusicViewHoder(var binding: ItemMusicListBinding) : RecyclerView.ViewHolder(binding.root)
}
