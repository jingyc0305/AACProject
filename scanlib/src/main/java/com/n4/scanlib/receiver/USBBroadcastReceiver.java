package com.n4.scanlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.n4.scanlib.Constant;
import com.n4.scanlib.manager.MediaStoreManager;


public class USBBroadcastReceiver extends BroadcastReceiver {

    private String TAG = getClass().getName();
    private MediaStoreManager mediaStoreManager ;

    @Override
    public void onReceive(Context context, Intent intent) {
        mediaStoreManager = MediaStoreManager.getMediaStoreManagerInstance(context);
        String action = intent.getAction();

        if (action != null) {
            if (action.equals("com.n4.devmgr.add")) {
                Log.d(TAG, "com.n4.devmgr.add");
                mediaStoreManager.notifyUsbState(Constant.MediaStatus.MEDIA_ADD);

            } else if (action.equals("com.n4.devmgr.remove")) {
                Log.d(TAG, "com.n4.devmgr.remove");
                mediaStoreManager.notifyUsbState(Constant.MediaStatus.MEDIA_REMOVE);

            } else if (action.equals("com.n4.devmgr.scanStart")) {
                Log.d(TAG, "com.n4.devmgr.scanStart");
                mediaStoreManager.notifyUsbState(Constant.MediaStatus.MEDIA_SCANNER_START);

            } else if (action.equals("com.n4.devmgr.scanfinish")) {
                Log.d(TAG, "com.n4.devmgr.scanfinish");
                mediaStoreManager.notifyUsbState(Constant.MediaStatus.MEDIA_SCANNER_FINISH);

            }


        }
    }

}
