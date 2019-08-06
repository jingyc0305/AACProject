package com.n4.scanlib.lisenter;

public interface OnUsbStatusChangedListener {
    void onScanStart();
    void onScanFinish();
    void onUsbRemoved();
}
