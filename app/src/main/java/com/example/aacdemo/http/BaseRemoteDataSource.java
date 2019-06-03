package com.example.aacdemo.http;

import com.example.aacdemo.base.BaseResponse;
import com.example.aacdemo.base.BaseViewModel;
import com.example.aacdemo.base.interf.RequestCallBack;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public abstract class BaseRemoteDataSource {

    private CompositeDisposable compositeDisposable;

    private BaseViewModel baseViewModel;

    public BaseRemoteDataSource(BaseViewModel baseViewModel){
        this.baseViewModel = baseViewModel;
        compositeDisposable = new CompositeDisposable();
    }


    public <T> T getService(Class<T> cls){
        return RetrifitManager.getInstance().getService(cls,HttpConfig.BASE_URL_WEATHER);
    }

    private <T> ObservableTransformer<BaseResponse<T>,T> applySchedulers(){
        return RetrifitManager.getInstance().applySchedulers();
    }

    protected void execute(Observable observable, RequestCallBack requestCallBack){

        execute(observable,new BaseSubscriber(baseViewModel,requestCallBack),true);

    }

    private void execute(Observable observable, Observer observer,boolean isDismiss) {
        Disposable disposable = (Disposable) observable.throttleFirst(500, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(applySchedulers())
                .compose(isDismiss?loadingTransformer():loadingTransformerWithoutDismiss())
                .subscribeWith(observer);
        addDisposable(disposable);


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
                .doOnSubscribe(disposable -> BaseRemoteDataSource.this.startLoading())
                .doFinally(() -> BaseRemoteDataSource.this.dismissLoading());
    }

    private <T> ObservableTransformer<T, T> loadingTransformerWithoutDismiss() {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((Consumer<Disposable>) disposable -> {
                    BaseRemoteDataSource.this.startLoading();
                });
    }

}
