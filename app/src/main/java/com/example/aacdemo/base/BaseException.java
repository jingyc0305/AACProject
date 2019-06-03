package com.example.aacdemo.base;

import com.example.aacdemo.http.HttpCode;

public class BaseException extends RuntimeException{
    private int errorCode = HttpCode.UNKNOWN_ERROR;
    public BaseException(){

    }
    public BaseException(int errorCode,String errorMessage){

        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
