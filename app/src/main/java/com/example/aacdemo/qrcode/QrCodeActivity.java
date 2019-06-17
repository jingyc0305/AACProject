package com.example.aacdemo.qrcode;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import com.example.aacdemo.R;
import com.example.aac_library.base.view.BaseActivity;
import com.example.aacdemo.databinding.ActivityQrcodeBinding;

public class QrCodeActivity extends BaseActivity {
    QrCodeViewModel qrCodeViewModel;
    QrCode qrCode;
    ActivityQrcodeBinding viewDataBinding;

    @Override
    protected ViewModel initViewModel() {
        qrCodeViewModel = ViewModelProviders.of(this).get(QrCodeViewModel.class);
        qrCodeViewModel.setLifecycleOwner(this);
        qrCodeViewModel.getQrCodeLiveData().observe(this,
                qrCode -> {
                    this.qrCode = qrCode;
                    viewDataBinding.imageView2.setImageBitmap(qrCode.getBitmap());
                });
        return qrCodeViewModel;
    }

    @Override
    protected int initLayoutResId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initData() {
        qrCodeViewModel.createQrCode("https://github.com/leavesC", 200);
    }

    @Override
    protected void initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode);
        viewDataBinding.setQrCode(qrCode);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewDataBinding != null) {
            viewDataBinding.unbind();
        }
    }
}