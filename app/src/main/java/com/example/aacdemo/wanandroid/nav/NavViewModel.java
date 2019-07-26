package com.example.aacdemo.wanandroid.nav;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.example.aac_library.base.BaseViewModel;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 19:18
 * @desc:
 */
public class NavViewModel extends BaseViewModel {
    MutableLiveData<NavDataBean> mNavLiveData;

    NavRepo navRepo;

    public NavViewModel() {
        mNavLiveData = new MutableLiveData<>();
        navRepo = new NavRepo(new NavDataSource(this));
    }

    void youBussiness() {
        navRepo.yourBussiness().observe(getLifecycleOwner(), new Observer<NavDataBean>() {
            @Override
            public void onChanged(NavDataBean navDataBean) {
                mNavLiveData.setValue(navDataBean);
            }
        });
    }

    public MutableLiveData<NavDataBean> getNavLiveData() {
        return mNavLiveData;
    }
}
