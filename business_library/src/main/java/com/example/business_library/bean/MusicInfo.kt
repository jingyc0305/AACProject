package com.example.business_library.bean

import android.graphics.Bitmap

/**
 * @author: JingYuchun
 * @date: 2019/8/5 15:12
 * @desc:
 */
data class MusicInfo(
    //专辑名
    var musicAlum: String,
    //歌曲名
    var musicName: String,
    //歌手
    var musicActor: String,
    //专辑图
    var musicImgUrl: String,
    //歌词
    var musicLrc: String
)