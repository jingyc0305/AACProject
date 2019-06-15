package com.example.aac_library.base;

import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aac_library.http.RetrifitManager;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * 抽象的接口调用者
 * 将retrofitManager baseSubscriber requestCallback 串联起来
 */
public abstract class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    private BaseViewModel baseViewModel;

    public BaseRemoteDataSource(BaseViewModel baseViewModel){
        this.baseViewModel = baseViewModel;
        compositeDisposable = new CompositeDisposable();
    }


    public <T> T getService(Class<T> cls){
        return RetrifitManager.getInstance().getService(cls);
    }
    public <T> T getService(Class<T> cls,String baseUrl){
        return RetrifitManager.getInstance().getService(cls,baseUrl);
    }
    private <T> ObservableTransformer<BaseResponse<T>,T> applySchedulers(){
        return RetrifitManager.getInstance().applySchedulers();
    }

    protected void execute(Observable observable, RequestCallBack requestCallBack){

        execute(observable,new BaseSubscriber(baseViewModel,requestCallBack),true);

    }

    private void execute(Observable observable, Observer observer,boolean isDismiss) {
        try {
            Disposable disposable = (Disposable) observable.throttleFirst(500, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(applySchedulers())
                    .compose(isDismiss?loadingTransformer():loadingTransformerWithoutDismiss())
                    .subscribeWith(observer);
            addDisposable(disposable);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void startLoading() {
        if (baseViewModel != null) {
            baseViewModel.startLoading();
        }
    }

    private void dismissLoading() {
        if (baseViewModel != null) {
            baseViewModel.dismissLoading();
        }
    }

    private <T> ObservableTransformer<T, T> loadingTransformer() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> startLoading())
                .doFinally(() -> BaseRemoteDataSource.this.dismissLoading());
    }

    private <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((Consumer<Disposable>) disposable -> {
                    startLoading();
                });
    }

}
