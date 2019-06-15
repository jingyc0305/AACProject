package com.example.aacdemo.qrcode;

import android.graphics.Bitmap;
import androidx.lifecycle.MutableLiveData;
import com.example.aac_library.base.BaseException;
import com.example.aac_library.base.BaseRepo;
import com.example.aac_library.base.interf.RequestCallBack;
import com.example.aacdemo.utils.Base64Util;

public class QrCodeRepo extends BaseRepo<IQrCodeData> {
    private MutableLiveData<QrCode> mutableLiveData;

    public QrCodeRepo(IQrCodeData remoteDataSource) {
        super(remoteDataSource);
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<QrCode> createQrCode(String text, int width) {
        remoteDataSource.createQrCode(text, width, new RequestCallBack<QrCode>() {
            @Override
            public void onSucess(QrCode qrCode) {
                //base64转换bitmap
                Bitmap bitmap = Base64Util.base64ToBitmap(qrCode.getBase64_image());
                qrCode.setBitmap(bitmap);
                mutableLiveData.setValue(qrCode);
            }

            @Override
            public void onFailed(BaseException error) {

            }
        });
        return mutableLiveData;
    }

}
