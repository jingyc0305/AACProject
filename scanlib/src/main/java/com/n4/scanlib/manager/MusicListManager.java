package com.n4.scanlib.manager;

import android.content.Context;

import com.n4.scanlib.Constant;
import com.n4.scanlib.lisenter.OnMusicListChangeListener;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.util.DbMusicUtils;

import java.util.ArrayList;
import java.util.List;


public class MusicListManager {

    private static volatile MusicListManager instance = null;
    private final Context mContext;
    private List<OnMusicListChangeListener> onMusicListChangeListenerList = new ArrayList<>();
    private MusicListManager(Context context) {
        mContext = context;

    }



    public static MusicListManager getMusicListManagerInstance(Context context) {
        if (instance == null) {
            synchronized (MusicListManager.class) {
                if (instance == null) {
                    instance = new MusicListManager(context);
                }
            }
        }
        return instance;
    }


    public void setOnMusicListChangeListener(OnMusicListChangeListener listChangeListener){
        this.onMusicListChangeListenerList.add(listChangeListener);
    }


    /**
     * 获取所有U盘歌曲
     * @return
     */
    public ArrayList<MusicBean> getUsbMusicList(){
        return MediaStoreManager.getMediaStoreManagerInstance(mContext).getAllMusic(DevManager.getDevManagerInstance(mContext).getDevInfo());
    }



    /**
     * 获取所有本地歌曲
     * @return
     */
    public List<MusicBean> getAllLocalMusicList(){
        return DbMusicUtils.getDbMusicUtilsInstance(mContext).queryAllLocalMusic();
    }

    /**
     * 获取所有收藏歌曲
     * @return
     */
    public List<MusicBean> getFavourList(){
        return DbMusicUtils.getDbMusicUtilsInstance(mContext).queryLocalMusicFavour();
    }

    /**
     * 获取所有收藏歌曲
     * @return
     */
    public List<CustomBean> getCustomList(){
        return DbMusicUtils.getDbMusicUtilsInstance(mContext).queryCustomList();
    }

    public void notifyList(int listType){
        switch (listType){
            case Constant.ALL_MUSIC:
                for(OnMusicListChangeListener listener:onMusicListChangeListenerList){
                    if(listener!=null){
                        listener.OnUsbMusicChanged();
                    }
                }

                break;
            case Constant.LOCAL_MUSIC_LIST:
                for(OnMusicListChangeListener listener:onMusicListChangeListenerList){
                    if(listener!=null){
                        listener.OnLocalMusicChanged();
                    }
                }
                break;
            case Constant.FAVOURITE:
                for(OnMusicListChangeListener listener:onMusicListChangeListenerList){
                    if(listener!=null){
                        listener.onFavourMusicChanged();
                    }
                }
                break;
            case Constant.CUSTOM_LIST:
                for(OnMusicListChangeListener listener:onMusicListChangeListenerList){
                    if(listener!=null){
                        listener.onCostomListChanged();
                    }
                }
                break;
            default:
                break;
        }


    }

}
