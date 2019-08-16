package com.example.roomlib.api

import androidx.lifecycle.LiveData
import com.example.roomlib.IMusicRoomCallBack
import com.example.roomlib.db.Music

/**
 * @author: JingYuchun
 * @date: 2019/8/10 12:04
 * @desc: 模拟音乐模块的业务-数据库操作
 */
interface IMusicData {
    /**
     * 添加一首音乐
     */
    fun addMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 删除一首音乐
     */
    fun deleteMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 删除所有音乐
     */
    fun deleteAllMusic(iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 修改音乐
     */
    fun updateMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 查询音乐列表
     */
    fun queryAllMusic():LiveData<List<Music>>?
}