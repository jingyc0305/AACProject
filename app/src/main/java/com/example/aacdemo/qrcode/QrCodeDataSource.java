package com.example.aacdemo.qrcode;

import com.example.aacdemo.api.ApiService;
import com.example.aac_library.base.BaseRemoteDataSource;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aac_library.http.HttpConfig;
/**
 * @author: JingYuchun
 * @date: 
 * @desc:
 */
public class QrCodeDataSource extends BaseRemoteDataSource implements IQrCodeData{
    public QrCodeDataSource(BaseViewModel baseViewModel) {
        super(baseViewModel);
    }

    @Override
    public void createQrCode(String text, int width, RequestCallBack requestCallBack) {

        //execute(getService(ApiService.class, HttpConfig.BASE_URL_QR_CODE).createQrCode(text,width),requestCallBack);
    }
}
