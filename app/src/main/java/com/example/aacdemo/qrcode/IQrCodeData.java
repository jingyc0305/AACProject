package com.example.aacdemo.qrcode;

import com.example.aac_library.base.interf.RequestCallBack;
/**
 * @author: JingYuchun
 * @date:
 * @desc:
 */
public interface IQrCodeData {

    /**
     * 创建二维码
     *
     * @param text 文本信息
     * @param width 二维码宽度
     * @param requestCallBack 请求回调
     */
    void createQrCode(String text, int width, RequestCallBack<QrCode> requestCallBack);
}
