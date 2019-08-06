package com.n4.scanlib.manager;

import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.UsbDevInfo;
import android.util.Log;

import java.util.ArrayList;

import static android.content.Context.STORAGE_SERVICE;

public class DevManager {
    private String TAG = getClass().getName();
    private Context mContext;
    private static volatile DevManager instance = null;

    private DevManager(Context context) {
        this.mContext = context;
    }

    public static DevManager getDevManagerInstance(Context context) {
        if (instance == null) {
            synchronized (DevManager.class) {
                if (instance == null) {
                    instance = new DevManager(context);
                }
            }
        }
        return instance;
    }

    public ArrayList<String> getDevInfo() {
        StorageManager storageManager = (StorageManager) mContext.getSystemService(STORAGE_SERVICE);

        UsbDevInfo[] mUsbList = storageManager.getCurrentUsbInfo();
        ArrayList<String> mList = new ArrayList<>();
        int usb_count = mUsbList.length;
        Log.d(TAG, "current_device_count = " + usb_count);
        if (usb_count > 0) {
            for (UsbDevInfo info : mUsbList) {
                Log.d(TAG, "current_device_count = " + info.fsuuid + "  status：" + info.status + "  path:" + info.path);
                if (info.status == StorageManager.USB_STATUS_USABLE) {
                    mList.add(0, info.fsuuid);
                }
            }
        }
        return mList;
    }

}
