package com.example.aac_library.http.execption;

import com.example.aac_library.base.BaseException;
import com.example.aac_library.http.HttpCode;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException(){
        super(HttpCode.TOKEN_INVALID,"Token失效");
    }

}
