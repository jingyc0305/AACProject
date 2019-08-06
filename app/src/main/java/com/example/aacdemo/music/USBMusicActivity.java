package com.example.aacdemo.music;

import androidx.lifecycle.ViewModelProviders;
import com.example.aac_library.base.view.BaseActivity;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.utils.util.LoggerUtil;
import com.example.aacdemo.R;
import com.n4.scanlib.dao.MusicBean;
import com.n4.scanlib.repo.USBMusicViewModel;

/**
 * @author:
 * @date: 2019年8月6日
 * @desc: USB Music UI
 */
public class USBMusicActivity extends BaseActivity {
    private static final String TAG = "UsbMusicActivity";
    private USBMusicViewModel mUSBMusicViewModel;

    @Override
    protected int initLayoutResId() {
        return R.layout.activity_usbmusic;
    }

    @Override
    protected void initDataBinding() {
        // TODO 暂时不用
    }

    @Override
    protected void initView() {
        //初始化视图控件
    }

    @Override
    protected void initData() {
        //获取音乐列表数据
        mUSBMusicViewModel.getUsbMusicList();
    }

    @Override
    protected BaseViewModel initViewModel() {
        mUSBMusicViewModel = ViewModelProviders.of(this).get(USBMusicViewModel.class);
        mUSBMusicViewModel.setLifecycleOwner(this);
        mUSBMusicViewModel.getUSBMusicRepoLiveData().observe(this, musicBeans -> {
            for (MusicBean musicBean: musicBeans){
                LoggerUtil.d(TAG, musicBean.toString());
            }
        });
        return mUSBMusicViewModel;
    }

    @Override
    protected void register() {
        //注册广播等
    }

    @Override
    protected void unRegister() {
        //反注册广播等
    }
}
