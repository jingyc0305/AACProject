package com.example.aacdemo.qrcode;

import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseViewModel;

public class QrCodeViewModel extends BaseViewModel {

    MutableLiveData<QrCode> mQrCodeLiveData;

    QrCodeRepo qrCodeRepo;
    public QrCodeViewModel(){
        mQrCodeLiveData = new MutableLiveData<>();
        qrCodeRepo = new QrCodeRepo(new QrCodeDataSource(this));
    }
    void createQrCode(String text,int width){
        qrCodeRepo.createQrCode(text,width).observe(getLifecycleOwner(), qrCode -> mQrCodeLiveData.setValue(qrCode));
    }

    public MutableLiveData<QrCode> getQrCodeLiveData() {
        return mQrCodeLiveData;
    }
}
