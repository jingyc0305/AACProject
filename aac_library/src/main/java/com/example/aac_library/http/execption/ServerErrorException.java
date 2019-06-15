package com.example.aac_library.http.execption;

import com.example.aac_library.base.BaseException;
import com.example.aac_library.http.HttpCode;

public class ServerErrorException extends BaseException {

    public ServerErrorException(){
        super(HttpCode.SERVER_ERROR,"服务器罢工了");
    }

}
