package com.n4.scanlib.repo;

import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseRepo;
import com.example.aac_library.base.interf.RequestCallBack;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;

import java.util.ArrayList;

/**
 * 数据源
 */
public class USBMusicRepo extends BaseRepo<IUSBMusicData> {
    private MutableLiveData<Boolean> newCustomLiveData;
    MutableLiveData<ArrayList<MusicBean>> usbMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> localMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> customMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> localFavourMusicListLiveData;
    boolean newCustomFlag = false;
    public USBMusicRepo(IUSBMusicData remoteDataSource) {
        super(remoteDataSource);
        newCustomLiveData = new MutableLiveData<>();
        usbMusicListLiveData = new MutableLiveData<>();
        localMusicListLiveData = new MutableLiveData<>();
        customMusicListLiveData = new MutableLiveData<>();
        localFavourMusicListLiveData = new MutableLiveData<>();
    }
    //以下为对应具体的业务接口
    // --------------------------------------------

    public boolean newCustom(String customName) {

        remoteDataSource.newCustom(customName, new RequestCallBack<Boolean>() {
            @Override
            public void onSucess(Boolean aBoolean) {
                newCustomFlag = aBoolean;
                newCustomLiveData.setValue(aBoolean);
            }

            @Override
            public void onFailed(BaseException error) {
                newCustomFlag = false;
            }
        });
        return newCustomFlag;
    }

    public boolean changeCustomName(Long customId, String newName) {
        return false;
    }

    public boolean deleteCustom(CustomBean customBean) {
        return false;
    }

    public void setFavour(MusicBean musicBean, int favourType) {
        remoteDataSource.setFavour(musicBean,favourType,null);
    }

    public void favourLocalMusic(MusicBean bean, int favourType) {

    }

    public boolean favourUsbMusic(MusicBean data, int favourType) {
        return false;
    }

    public boolean deleteMusicFromDb(MusicBean data) {
        return false;
    }

    public void copyMusicToLocal(MusicBean musicBean) {

    }

    public MutableLiveData<ArrayList<MusicBean>> getUsbMusicList() {

        remoteDataSource.getUsbMusicList(new RequestCallBack<ArrayList<MusicBean>>() {
            @Override
            public void onFailed(BaseException error) {
                throw error;
            }

            @Override
            public void onSucess(ArrayList<MusicBean> musicBeans) {
                usbMusicListLiveData.setValue(musicBeans);
            }
        });
        return usbMusicListLiveData;
    }

    public MutableLiveData<ArrayList<MusicBean>> getAllLocalMusicList() {
        remoteDataSource.getAllLocalMusicList(new RequestCallBack<ArrayList<MusicBean>>() {
            @Override
            public void onFailed(BaseException error) {

            }

            @Override
            public void onSucess(ArrayList<MusicBean> musicBeans) {
                localMusicListLiveData.setValue(musicBeans);
            }
        });
        return localMusicListLiveData;
    }

    public MutableLiveData<ArrayList<MusicBean>> getFavourList() {
        remoteDataSource.getFavourList(new RequestCallBack<ArrayList<MusicBean>>() {
            @Override
            public void onFailed(BaseException error) {

            }
            @Override
            public void onSucess(ArrayList<MusicBean> musicBeans) {
                localFavourMusicListLiveData.setValue(musicBeans);
            }
        });
        return localFavourMusicListLiveData;
    }
}
