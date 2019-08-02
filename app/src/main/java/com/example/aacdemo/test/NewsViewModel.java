package com.example.aacdemo.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseViewModel;

public class NewsViewModel extends BaseViewModel {

    MutableLiveData<NewsDataBean> mNewsRepoLiveData;

    NewsRepo mNewsRepo;

    public NewsViewModel() {
        mNewsRepoLiveData = new MutableLiveData<>();
        mNewsRepo = new NewsRepo(new NewsDataSource(this));
    }

    public void yourBusinessInterface() {
        mNewsRepo.yourBusinessInterface().observe(getLifecycleOwner(), new Observer<NewsDataBean>() {
            @Override
            public void onChanged(NewsDataBean data) {
                mNewsRepoLiveData.setValue(data);
            }
        });
    }

    public MutableLiveData<NewsDataBean> getNewsRepoLiveData() {
        return mNewsRepoLiveData;
    }
}
