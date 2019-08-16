package com.example.business_library.repository

import com.example.business_library.BlEConnectCallBack
import com.example.business_library.bean.MusicInfo
import com.example.business_library.bean.MusicState
import com.example.aac_library.base.interf.RequestCallBack as RequestCallBack1


/**
 * @author: JingYuchun
 * @date: 2019/6/27 14:01
 * @desc: 具体业务接口
 */
interface IHomeData {
    /**
     * 查询首页文字列表
     */
    fun <T : Any?> queryHomeArticleData(pageIndex:Int, requestCallBack: RequestCallBack1<T>, isRefresh : Boolean)

    /**
     * 查询首页banner数据
     */
    fun <T : Any?> queryHomeBannerData(requestCallBack: RequestCallBack1<T>)


    /**
     * 模拟车机业务: 连接蓝牙
     */
    fun connectBLEtooth(isAuto:Boolean,blEConnectCallBack: BlEConnectCallBack)

    /**
     * 播放 蓝牙音乐
     */
    fun playBTMusic()
    /**
     * 播放 USB音乐
     */
    fun playUSBMusic()
    /**
     * 暂停音乐
     */
    fun pauseMusic()
    /**
     * 下一曲
     */
    fun playNext()
    /**
     * 上一曲
     */
    fun playPre()

    /**
     * 获取歌曲信息
     */
    fun getMusicInfo(): MusicInfo?

    /**
     * 获取当前音乐状态
     */
//    fun getCurrMusicState(): MusicState?

}