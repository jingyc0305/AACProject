package com.example.aac_library.http.execption;

import com.example.aac_library.base.BaseException;
import com.example.aac_library.http.HttpCode;

public class TimeOutException extends BaseException {

    public TimeOutException(){
        super(HttpCode.NETWORK_TIME_OUT,"连接超时");
    }

}
