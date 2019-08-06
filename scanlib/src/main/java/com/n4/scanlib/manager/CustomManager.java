package com.n4.scanlib.manager;

import android.content.Context;

import com.n4.scanlib.Constant;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.util.DbMusicUtils;

import org.greenrobot.greendao.annotation.NotNull;

public class CustomManager {

    private String TAG = "music-" + CustomManager.class.getSimpleName();
    private static Context mContext;

    private static volatile CustomManager instance = null;
    private MediaStoreManager mediaStoreManager;
    private DbMusicUtils dbMusicUtils;

    private CustomManager(Context context) {
        mContext = context;
        mediaStoreManager = MediaStoreManager.getMediaStoreManagerInstance(mContext);
        dbMusicUtils = DbMusicUtils.getDbMusicUtilsInstance(mContext);
    }

    public static CustomManager getCustomManagerInstance(Context context) {
        if (instance == null) {
            synchronized (CustomManager.class) {
                if (instance == null) {
                    instance = new CustomManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    /**
     * 新建歌单
     */
    public boolean newCustom(String customName){

        boolean b  = false;

        if(customName ==null){
           return b;
        }

        b = dbMusicUtils.insertCustom(customName);
        if(b){
            MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.CUSTOM_LIST);
        }

        return b;
    }

    /**
     * 歌单改名
     */
    public boolean changeCustomName(Long customId,String newName){

        boolean b  = false;

        if(customId ==null||newName ==null){
            return b;
        }

        CustomBean customBean = new CustomBean();
        customBean.setCustom_id(customId);
        customBean.setCustom_name(newName);
        b = dbMusicUtils.upDateCustom(customBean);
        if(b){
            MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.CUSTOM_LIST);
        }

        return b;
    }

    /**
     * 删除歌单
     */
    public boolean deleteCustom(CustomBean customBean){

        boolean b  = false;

        if(customBean ==null){
            return b;
        }

        b = dbMusicUtils.deleteCustom(customBean);
        if(b){
            MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.CUSTOM_LIST);
        }
        return b;
    }

}
