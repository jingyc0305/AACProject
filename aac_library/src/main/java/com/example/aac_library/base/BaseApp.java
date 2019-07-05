package com.example.aac_library.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
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
//        SkinCompatManager.withoutActivity(this)
//                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
//                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
//                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
//                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
//                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
//                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
//                .loadSkin();
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
