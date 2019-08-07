package com.n4.scanlib.repo;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.utils.util.LoggerUtil;
import com.n4.scanlib.dao.CustomBean;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.lisenter.OnMusicListChangeListener;
import com.n4.scanlib.lisenter.OnUsbStatusChangedListener;
import com.n4.scanlib.manager.MediaStoreManager;
import com.n4.scanlib.manager.MusicListManager;

import java.util.ArrayList;

/**
 * @author:
 * @date: 2019年8月6日
 * @desc: 业务调度处理
 */
public class USBMusicViewModel extends BaseViewModel implements OnMusicListChangeListener, OnUsbStatusChangedListener {
    private static final String TAG = "USBMusicViewModel";
    /**
     * 数据容器
     */
    MutableLiveData<ArrayList<MusicBean>> usbMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> localMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> customMusicListLiveData;
    MutableLiveData<ArrayList<MusicBean>> localFavourMusicListLiveData;
    /**
     * 数据仓库 负责调度取真正的数据
     */
    USBMusicRepo mUSBMusicRepo;
    /**
     * 列表类型
     */
    private final static int USB_MUSIC = 0;
    private final static int LOCAL_MUSIC = 1;
    private final static int LOCAL_FAVOUR = 2;
    private final static int CUSTOM_LIST = 3;

    /**
     * 初始化 调度器和数据容器
     * 设置一些监听器
     *
     * @param context
     */
    public USBMusicViewModel(Context context) {
        usbMusicListLiveData = new MutableLiveData<>();
        localMusicListLiveData = new MutableLiveData<>();
        customMusicListLiveData = new MutableLiveData<>();
        localFavourMusicListLiveData = new MutableLiveData<>();
        mUSBMusicRepo = new USBMusicRepo(new USBMusicDataSource(context, this));
        //设置音乐列表变更监听器
        MusicListManager.getMusicListManagerInstance(context).setOnMusicListChangeListener(this);
        //设置USB动作变动监听器
        MediaStoreManager.getMediaStoreManagerInstance(context).setOnUsbStateChangeListener(this);
    }

    //以下为对应具体的业务接口
    // --------------------------------------------

    public boolean newCustom(String customName) {
        return mUSBMusicRepo.newCustom(customName);
    }

    public boolean changeCustomName(Long customId, String newName) {
        return mUSBMusicRepo.changeCustomName(customId, newName);
    }

    public boolean deleteCustom(CustomBean customBean) {
        return mUSBMusicRepo.deleteCustom(customBean);
    }

    public void setFavour(MusicBean musicBean, int favourType) {
        mUSBMusicRepo.setFavour(musicBean, favourType);
    }

    public void favourLocalMusic(MusicBean bean, int favourType) {
        mUSBMusicRepo.favourLocalMusic(bean, favourType);
    }

    public boolean favourUsbMusic(MusicBean data, int favourType) {
        return mUSBMusicRepo.favourUsbMusic(data, favourType);
    }

    public boolean deleteMusicFromDb(MusicBean data) {
        return mUSBMusicRepo.deleteMusicFromDb(data);
    }

    public void copyMusicToLocal(MusicBean musicBean) {
        mUSBMusicRepo.copyMusicToLocal(musicBean);
    }

    public void getUsbMusicList() {
        mUSBMusicRepo.getUsbMusicList().observe(getLifecycleOwner(), new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged(ArrayList<MusicBean> musicBeans) {
                usbMusicListLiveData.setValue(musicBeans);
            }
        });
    }

    public void getAllLocalMusicList() {
        mUSBMusicRepo.getAllLocalMusicList().observe(getLifecycleOwner(), new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged(ArrayList<MusicBean> musicBeans) {
                localMusicListLiveData.setValue(musicBeans);
            }
        });
    }

    public void getFavourList() {
        mUSBMusicRepo.getFavourList().observe(getLifecycleOwner(), new Observer<ArrayList<MusicBean>>() {
            @Override
            public void onChanged(ArrayList<MusicBean> musicBeans) {
                localFavourMusicListLiveData.setValue(musicBeans);
            }
        });
    }

    public MutableLiveData<ArrayList<MusicBean>> getCustomMusicListLiveData() {
        return customMusicListLiveData;
    }

    public MutableLiveData<ArrayList<MusicBean>> getLocalFavourMusicListLiveData() {
        return localFavourMusicListLiveData;
    }

    public MutableLiveData<ArrayList<MusicBean>> getLocalMusicListLiveData() {
        return localMusicListLiveData;
    }

    public MutableLiveData<ArrayList<MusicBean>> getUsbMusicListLiveData() {
        return usbMusicListLiveData;
    }

    /**
     * 刷新列表数据
     *
     * @param type
     */
    private void upDateUi(int type) {
        switch (type) {
            case USB_MUSIC:
                getUsbMusicList();
                break;
            case LOCAL_MUSIC:
                getAllLocalMusicList();
                break;
            case LOCAL_FAVOUR:
                getFavourList();
                break;
            case CUSTOM_LIST:
                //getCustomList();
                break;
            default:
                break;
        }

    }

    /**
     * 音乐列表 监听回调
     */
    @Override
    public void OnUsbMusicChanged() {
        upDateUi(USB_MUSIC);
    }

    @Override
    public void OnLocalMusicChanged() {
        upDateUi(LOCAL_MUSIC);
    }

    @Override
    public void onFavourMusicChanged() {
        upDateUi(LOCAL_FAVOUR);
    }

    @Override
    public void onCostomListChanged() {
        upDateUi(CUSTOM_LIST);
    }


    /**
     * USB 动作监听回调
     */

    @Override
    public void onScanStart() {
        LoggerUtil.d(TAG, "onScanStart");
    }

    @Override
    public void onScanFinish() {
        upDateUi(USB_MUSIC);
    }

    @Override
    public void onUsbRemoved() {
        Log.d(TAG, "onUsbRemoved");
    }
}
