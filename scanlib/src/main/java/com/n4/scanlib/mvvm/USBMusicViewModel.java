package com.n4.scanlib.mvvm;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseViewModel;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;

import java.util.ArrayList;

/**
 * @author:
 * @date: 2019年8月6日
 * @desc: 业务调度处理
 */
public class USBMusicViewModel extends BaseViewModel {

    MutableLiveData<ArrayList<MusicBean>> mUSBMusicRepoLiveData;

    USBMusicRepo mUSBMusicRepo;

    public USBMusicViewModel(Context context) {
        mUSBMusicRepoLiveData = new MutableLiveData<>();
        mUSBMusicRepo = new USBMusicRepo(new USBMusicDataSource(context,this));
    }

    //以下为对应具体的业务接口
    // --------------------------------------------

    public boolean newCustom(String customName) {
        return mUSBMusicRepo.newCustom(customName);
    }

    public boolean changeCustomName(Long customId, String newName) {
        return mUSBMusicRepo.changeCustomName(customId,newName);
    }

    public boolean deleteCustom(CustomBean customBean) {
        return mUSBMusicRepo.deleteCustom(customBean);
    }

    public void setFavour(MusicBean musicBean, int favourType) {
        mUSBMusicRepo.setFavour(musicBean,favourType);
    }

    public void favourLocalMusic(MusicBean bean, int favourType) {
        mUSBMusicRepo.favourLocalMusic(bean,favourType);
    }

    public boolean favourUsbMusic(MusicBean data, int favourType) {
        return mUSBMusicRepo.favourUsbMusic(data,favourType);
    }

    public  boolean deleteMusicFromDb(MusicBean data) {
        return mUSBMusicRepo.deleteMusicFromDb(data);
    }

    public void copyMusicToLocal(MusicBean musicBean) {
        mUSBMusicRepo.copyMusicToLocal(musicBean);
    }

    public void getUsbMusicList() {
        mUSBMusicRepo.getUsbMusicList().observe(getLifecycleOwner(), new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged(ArrayList<MusicBean> musicBeans) {
                mUSBMusicRepoLiveData.setValue(musicBeans);
            }
        });
    }

    public ArrayList<MusicBean> getAllLocalMusicList() {
        return mUSBMusicRepo.getAllLocalMusicList();
    }

    public ArrayList<MusicBean> getFavourList() {
        return mUSBMusicRepo.getFavourList();
    }

    public MutableLiveData<ArrayList<MusicBean>> getUSBMusicRepoLiveData() {
        return mUSBMusicRepoLiveData;
    }
}
