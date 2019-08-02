package com.example.aacdemo.test;

import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseRepo;
import com.example.aac_library.base.interf.RequestCallBack;

/**
 * 数据源
 */
public class NewsRepo extends BaseRepo<INewsData> {
    private MutableLiveData<NewsDataBean> mutableLiveData;

    public NewsRepo(INewsData remoteDataSource) {
        super(remoteDataSource);
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<NewsDataBean> yourBusinessInterface() {
        remoteDataSource.yourBusinessInterface(new RequestCallBack<NewsDataBean>() {
            @Override
            public void onSucess(NewsDataBean data) {
                mutableLiveData.setValue(data);
            }

            @Override
            public void onFailed(BaseException error) {

            }
        });
        return mutableLiveData;
    }
}
