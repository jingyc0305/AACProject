package com.example.aac_library.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.example.aac_library.BuildConfig;
import com.example.aac_library.utils.util.LoggerUtil;
import com.sunchen.netbus.NetStatusBus;
//import skin.support.SkinCompatManager;
//import skin.support.app.SkinAppCompatViewInflater;
//import skin.support.app.SkinCardViewInflater;
//import skin.support.constraint.app.SkinConstraintViewInflater;
//import skin.support.design.app.SkinMaterialViewInflater;

public class BaseApp extends Application {

    private static BaseApp appContext;
    private int activityCount = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        NetStatusBus.getInstance().init(this);
        registerActivityLifecycleCallbacks(callbacks);

        // 初始化Looger工具
        LoggerUtil.init(BuildConfig.DEBUG);
    }

    public static BaseApp getAppContext() {
        return appContext;
    }


    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCount--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
