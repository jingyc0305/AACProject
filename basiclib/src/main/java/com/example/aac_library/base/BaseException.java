package com.example.aac_library.base;

import com.example.aac_library.http.HttpCode;

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
