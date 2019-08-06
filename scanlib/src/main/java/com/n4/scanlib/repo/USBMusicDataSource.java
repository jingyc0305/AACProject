package com.n4.scanlib.repo;

import android.content.Context;
import com.example.aac_library.base.BaseRemoteDataSource;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.interf.RequestCallBack;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.manager.MusicListManager;

import java.util.ArrayList;

/**
 * @author
 * @date 2019年8月6日
 * 具体业务处理
 */
public class USBMusicDataSource extends BaseRemoteDataSource implements IUSBMusicData {
    private Context context;
    public USBMusicDataSource(Context context,BaseViewModel baseViewModel) {
        super(baseViewModel);
        this.context = context;
    }

    @Override
    public boolean newCustom(String customName, RequestCallBack requestCallBack) {
        return false;
    }

    @Override
    public void getUsbMusicList(RequestCallBack requestCallBack) {
        MusicListManager.getMusicListManagerInstance(context).getUsbMusicList();
    }

    @Override
    public <T> boolean changeCustomName(Long customId, String newName, RequestCallBack<T> requestCallBack) {
        return false;
    }

    @Override
    public <T> boolean deleteCustom(CustomBean customBean, RequestCallBack<T> requestCallBack) {
        return false;
    }

    @Override
    public <T> void setFavour(MusicBean musicBean, int favourType, RequestCallBack<T> requestCallBack) {

    }

    @Override
    public <T> void favourLocalMusic(MusicBean bean, int favourType, RequestCallBack<T> requestCallBack) {

    }

    @Override
    public <T> boolean favourUsbMusic(MusicBean data, int favourType, RequestCallBack<T> requestCallBack) {
        return false;
    }

    @Override
    public <T> boolean deleteMusicFromDb(MusicBean data, RequestCallBack<T> requestCallBack) {
        return false;
    }

    @Override
    public <T> void copyMusicToLocal(MusicBean musicBean, RequestCallBack<T> requestCallBack) {

    }

    @Override
    public <T> ArrayList<MusicBean> getAllLocalMusicList(RequestCallBack<T> requestCallBack) {
        return null;
    }

    @Override
    public <T> ArrayList<MusicBean> getFavourList(RequestCallBack<T> requestCallBack) {
        return null;
    }
}
