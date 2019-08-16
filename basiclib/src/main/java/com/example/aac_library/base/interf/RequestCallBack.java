package com.example.aac_library.base.interf;

import com.example.aac_library.base.BaseException;

public interface RequestCallBack<T> {

    void onSucess(T t);

    void onFailed(BaseException error);
}
