package com.example.aacdemo.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("error_code")
    private int code;

    @SerializedName("reason")
    private String msg;

    @SerializedName("result")
    private T data;


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
