package com.example.aacdemo.wanandroid.nav;

import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseRepo;
import com.example.aac_library.base.interf.RequestCallBack;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 19:23
 * @desc:
 */
public class NavRepo extends BaseRepo<INavData> {
    private MutableLiveData<NavDataBean> mutableLiveData;

    public NavRepo(INavData remoteDataSource) {
        super(remoteDataSource);
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<NavDataBean> yourBussiness() {
        remoteDataSource.yourBussiness(new RequestCallBack<NavDataBean>() {
            @Override
            public void onSucess(NavDataBean qrCode) {
                mutableLiveData.setValue(qrCode);
            }

            @Override
            public void onFailed(BaseException error) {

            }
        });
        return mutableLiveData;
    }
}
