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

    private RequestCallBack requestCallBack;
    public BaseSubscriber(RequestCallBack requestCallBack){
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
        Log.d("okhttp", e.getMessage());
        if(null!= e && e instanceof NullPointerException){
            return;
        }else {
            if (requestCallBack != null) {
                requestCallBack.onFailed(new BaseException(HttpCode.UNKNOWN_ERROR,e.getMessage()));
            }
        }

    }

    @Override
    public void onComplete() {

    }
}
