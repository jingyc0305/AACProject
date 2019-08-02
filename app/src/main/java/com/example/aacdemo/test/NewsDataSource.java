package com.example.aacdemo.test;

import com.example.aac_library.base.BaseRemoteDataSource;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.interf.RequestCallBack;

/**
 * 业务处理,发起网络请求
 */
public class NewsDataSource extends BaseRemoteDataSource implements INewsData {
    public NewsDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void yourBusinessInterface(RequestCallBack requestCallBack) {
        //TODO 自定义接口 请创建ApiService类 by Retrifit
        //execute(getService(ApiService.class, "baseUrl").yourBusinessInterface(),requestCallBack);
    }
}
