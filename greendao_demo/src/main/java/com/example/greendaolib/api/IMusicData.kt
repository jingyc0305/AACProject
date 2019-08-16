package com.example.greendaolib.api

import androidx.lifecycle.LiveData
import com.example.greendaolib.IMusicRoomCallBack
import com.example.greendaolib.MusicBean
/**
 * @author: JingYuchun
 * @date: 2019/8/10 12:04
 * @desc: 模拟音乐模块的业务-数据库操作
 */
interface IMusicData {
    /**
     * 添加一首音乐
     */
    fun addMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 删除一首音乐
     */
    fun deleteMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 删除所有音乐
     */
    fun deleteAllMusic(iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 修改音乐
     */
    fun updateMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack)

    /**
     * 查询音乐列表
     */
    fun queryAllMusic():List<MusicBean>?
}