package com.example.aac_library.base;

import com.blankj.utilcode.util.AppUtils;
import com.example.aac_library.R;
import com.example.aac_library.base.interf.IBaseView;
import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aac_library.http.HttpCode;
import com.example.aac_library.http.RetrifitManager;
import com.example.aac_library.http.execption.ParamterInvalidException;
import com.example.aac_library.http.execption.ServerErrorException;
import com.example.aac_library.http.execption.TimeOutException;
import com.example.aac_library.http.execption.TokenInvalidException;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @author JingYuchun
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
    protected void execute(Observable observable, RequestCallBack requestCallBack,boolean isRefresh){

        execute(observable,new BaseSubscriber(requestCallBack),true,isRefresh);

    }

    private void execute(Observable observable, Observer observer,boolean isDismiss,boolean isRefresh) {
        try {
            Disposable disposable = (Disposable) observable.throttleFirst(500, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(applySchedulers())
                    .compose(isDismiss?loadingTransformer(isRefresh):loadingTransformerWithoutDismiss())
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
    private void showError(String msg) {
        if (baseViewModel != null) {
            baseViewModel.showError(msg);
        }
    }
    private void showEmpty() {
        if (baseViewModel != null) {
            baseViewModel.showEmpty();
        }
    }
    private <T> ObservableTransformer<T, T> loadingTransformer(boolean isRefresh) {
        return observable -> observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (isRefresh){
                        startLoading();
                    }
                })
                .doFinally(() -> dismissLoading());
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

    public <T> ObservableTransformer<BaseResponse<T>, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(result -> {
                    switch (result.getErrorCode()) {
                        case HttpCode.SUCCESS: {
                            return createData(result.getData());
                        }
                        case HttpCode.TOKEN_INVALID: {
                            showError(BaseApp.getAppContext().getResources().getString(R.string.token_invalid));
                            throw new TokenInvalidException();
                        }
                        case HttpCode.NETWORK_TIME_OUT: {
                            showError(BaseApp.getAppContext().getResources().getString(R.string.network_time_out));
                            throw new TimeOutException();
                        }
                        case HttpCode.PARAMETER_INVALID: {
                            showError(BaseApp.getAppContext().getResources().getString(R.string.parameter_invalid));
                            throw new ParamterInvalidException();
                        }
                        default: {
                            showError(BaseApp.getAppContext().getResources().getString(R.string.error_msg));
                            throw new ServerErrorException();
                        }
                    }
                });
    }

    private <T> ObservableSource<T> createData(final T data) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(data);
                emitter.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        });

    }
}
