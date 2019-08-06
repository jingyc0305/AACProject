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
    private MutableLiveData<ArrayList<MusicBean>> mutableLiveData;
    boolean newCustomFlag = false;
    public USBMusicRepo(IUSBMusicData remoteDataSource) {
        super(remoteDataSource);
        newCustomLiveData = new MutableLiveData<>();
        mutableLiveData = new MutableLiveData<>();
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
                mutableLiveData.setValue(musicBeans);
            }
        });
        return mutableLiveData;
    }

    public ArrayList<MusicBean> getAllLocalMusicList() {
        return null;
    }

    public ArrayList<MusicBean> getFavourList() {
        return null;
    }
}
