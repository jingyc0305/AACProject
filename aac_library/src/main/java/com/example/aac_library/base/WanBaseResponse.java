package com.example.aac_library.base;

import com.google.gson.annotations.SerializedName;

public class WanBaseResponse<T>{

    @SerializedName("errorCode")
    private int code;

    @SerializedName("errorMsg")
    private String msg;

    @SerializedName("data")
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
