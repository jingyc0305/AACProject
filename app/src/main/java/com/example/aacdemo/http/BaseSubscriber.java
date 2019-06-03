package com.example.aacdemo.http;

import android.widget.Toast;
import com.example.aacdemo.base.BaseApp;
import com.example.aacdemo.base.BaseViewModel;
import com.example.aacdemo.base.interf.RequestCallBack;
import io.reactivex.observers.DisposableObserver;

public class BaseSubscriber<T> extends DisposableObserver<T> {
    private BaseViewModel baseViewModel;

    private RequestCallBack requestCallBack;
    private BaseSubscriber(BaseViewModel baseViewModel){
        this.baseViewModel = baseViewModel;
    }
    public BaseSubscriber(BaseViewModel baseViewModel, RequestCallBack requestCallBack){
        this.baseViewModel = baseViewModel;
        this.requestCallBack = requestCallBack;
    }
    @Override
    public void onNext(T t) {
        if(requestCallBack != null){
            requestCallBack.onSucess(t);
        }

    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();

        if (baseViewModel == null) {
            Toast.makeText(BaseApp.getAppContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }else {
            baseViewModel.showToast(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
