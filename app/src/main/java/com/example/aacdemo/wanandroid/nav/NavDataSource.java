package com.example.aacdemo.wanandroid.nav;

import com.example.aac_library.base.BaseRemoteDataSource;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.interf.RequestCallBack;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 19:21
 * @desc:
 */
public class NavDataSource extends BaseRemoteDataSource implements INavData{
    public NavDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void yourBussiness(RequestCallBack requestCallBack) {
        //execute(getService(ApiService.class).yourBussiness(),requestCallBack);
    }
}
