package com.example.aac_library.http.execption;

import com.example.aac_library.base.BaseException;
import com.example.aac_library.http.HttpCode;

public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException(){
        super(HttpCode.PARAMETER_INVALID,"参数有误");
    }

}
