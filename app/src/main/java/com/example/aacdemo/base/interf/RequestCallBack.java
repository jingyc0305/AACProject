package com.example.aacdemo.base.interf;

import com.example.aacdemo.base.BaseException;

public interface RequestCallBack<T> {

    void onSucess(T t);

    void onFailed(BaseException error);
}
