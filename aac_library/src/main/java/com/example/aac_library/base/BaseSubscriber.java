package com.example.aac_library.base;

import android.util.Log;
import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aac_library.http.HttpCode;
import io.reactivex.observers.DisposableObserver;

/**
 * 自定义Observer 对请求结果进行自定义回调
 * @param <T>
 */
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
        Log.d("JingYuchun", e.getMessage());
        requestCallBack.onFailed(new BaseException(HttpCode.UNKNOWN_ERROR,e.getMessage()));
        if (baseViewModel == null) {
            throw new RuntimeException("the baseViewModel can not be null,please check your param of constructor.");
        }else {
            //baseViewModel.showToast(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
