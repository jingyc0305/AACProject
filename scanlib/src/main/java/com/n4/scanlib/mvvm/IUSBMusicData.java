package com.n4.scanlib.mvvm;

import com.example.aac_library.base.interf.RequestCallBack;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;

import java.util.ArrayList;

/**
 * @author:
 * @date: 2019/8/6 14:42
 * @desc: USB音乐接口
 */
public interface IUSBMusicData {
    /**
     * 新建歌单
     */
    boolean  newCustom(String customName, RequestCallBack requestCallBack);

    /**
     * 歌单改名
     */
    <T> boolean changeCustomName(Long customId, String newName, RequestCallBack<T> requestCallBack);

    /**
     * 删除歌单
     */
    <T> boolean deleteCustom(CustomBean customBean, RequestCallBack<T> requestCallBack);


    /**
     * 收藏/取消收藏
     *
     * @param musicBean
     * @param favourType
     */
    <T> void setFavour(MusicBean musicBean, int favourType, RequestCallBack<T> requestCallBack);

    /**
     * 本地歌曲收藏/取消收藏
     */
    <T> void favourLocalMusic(MusicBean bean, int favourType, RequestCallBack<T> requestCallBack);

    /**
     * u盘歌曲收藏/取消收藏
     */
    <T> boolean favourUsbMusic(MusicBean data, int favourType, RequestCallBack<T> requestCallBack);

    /**
     * 数据库删除歌曲数据
     */
    <T> boolean deleteMusicFromDb(MusicBean data, RequestCallBack<T> requestCallBack);

    /**
     * 复制u盘歌曲到本地
     */
    <T> void copyMusicToLocal(MusicBean musicBean, RequestCallBack<T> requestCallBack);


    /**
     * 获取所有U盘歌曲
     *
     * @return
     */
     void getUsbMusicList(RequestCallBack requestCallBack);

    /**
     * 获取所有本地歌曲
     *
     * @return
     */
    <T> ArrayList<MusicBean> getAllLocalMusicList(RequestCallBack<T> requestCallBack);

    /**
     * 获取所有收藏歌曲
     *
     * @return
     */
    <T> ArrayList<MusicBean> getFavourList(RequestCallBack<T> requestCallBack);
}
