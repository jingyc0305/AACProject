package com.n4.scanlib.manager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.n4.scanlib.Constant;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.util.DbMusicUtils;
import com.n4.scanlib.util.FileUtils;

import java.io.File;

public class FavManager {

    private String TAG = "music-" + FavManager.class.getSimpleName();
    private static Context mContext;

    private static volatile FavManager instance = null;
    private MediaStoreManager mediaStoreManager;
    private DbMusicUtils dbMusicUtils;

    private FavManager(Context context) {
        mContext = context;
        mediaStoreManager = MediaStoreManager.getMediaStoreManagerInstance(mContext);
        dbMusicUtils = DbMusicUtils.getDbMusicUtilsInstance(mContext);

    }

    public static FavManager getFavManagerInstance(Context context) {
        if (instance == null) {
            synchronized (FavManager.class) {
                if (instance == null) {
                    instance = new FavManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    /**
     * 收藏/取消收藏
     * @param musicBean
     * @param favourType
     */
    public void setFavour(MusicBean musicBean, int favourType){

        if(musicBean.getUuid().equals(Constant.UUID_LOCAL)){
            favourLocalMusic(musicBean,favourType);
        }else {
            mediaStoreManager.setFavourMusic(favourType,musicBean.getUuid(),musicBean.getMusic_name());
            favourUsbMusic(musicBean,favourType);

        }


    }


    /**
     * 本地歌曲收藏/取消收藏
     */
    public void favourLocalMusic(MusicBean bean,int favourType){

        if(favourType==Constant.TYPE_FAVOUR_MUSIC){
            bean.setFavour_type(1);
        }else if(favourType==Constant.TYPE_FAVOUR_MUSIC_NOT){
            bean.setFavour_type(0);
        }
        if(!dbMusicUtils.musicExistInLocalDB(bean)){
            Log.d(TAG,"favourLocalMusic: music is not exist");

        }else {

            boolean b = dbMusicUtils.updateLocalMusic(bean);
            if(b){
                MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.LOCAL_MUSIC_LIST);
                MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.FAVOURITE);



            }
        }
    }


    /**
     * u盘歌曲收藏/取消收藏
     */
    public boolean favourUsbMusic(MusicBean data,int favourType){


        if (data == null) {
            Log.d(TAG, "addMusicToDb: data is null");
            return false;
        }
        if(favourType==Constant.TYPE_FAVOUR_MUSIC){
            data.setFavour_type(1);
            if(!dbMusicUtils.musicExistInLocalDB(data)){

                String music_name = data.getMusic_name();
                String music_uuid = data.getUuid();
                String music_local_path = FileUtils.getLocalMusicPath(music_name, music_uuid);
                data.setUuid(Constant.UUID_LOCAL);
                data.setMusic_path(music_local_path);

               boolean b = DbMusicUtils.getDbMusicUtilsInstance(mContext).insertLocalMusic(data);
                if(b){
                    MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.LOCAL_MUSIC_LIST);
                }
                return b;
            }
        }else if(favourType==Constant.TYPE_FAVOUR_MUSIC_NOT){
            data.setFavour_type(0);
            if(dbMusicUtils.musicExistInLocalDB(data)){
//                deleteMusicFromDb(bean);
            }


        }

        return true;
    }




    /**
     * 数据库删除歌曲数据
     */
    public boolean deleteMusicFromDb(MusicBean data){
        if (data == null) {
            Log.d(TAG, "deleteMusicFromDb: data is null");
            return false;
        }
        boolean b = DbMusicUtils.getDbMusicUtilsInstance(mContext).deleteLocalMusic(data);
        if(b){
            MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.LOCAL_MUSIC_LIST);
        }
        return b;
    }


    /**
     * 复制u盘歌曲到本地
     */
    public void copyMusicToLocal(MusicBean musicBean){

    }


}
