package com.example.aacdemo.qrcode;

import com.example.aac_library.base.interf.RequestCallBack;

public interface IQrCodeData {

    void createQrCode(String text, int width, RequestCallBack<QrCode> requestCallBack);
}
