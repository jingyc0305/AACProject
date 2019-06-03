package com.example.aacdemo.base.interf;

public interface IView {
    void startLoading();
    void startLoading(String msg);
    void dismissLoading();
    void showToast(String msg);
    void finishWithResultOk();
}
