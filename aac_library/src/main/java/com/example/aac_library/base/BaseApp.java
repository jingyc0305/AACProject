package com.example.aac_library.base;

import android.app.Application;

public class BaseApp extends Application {

    private static BaseApp appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static BaseApp getAppContext() {
        return appContext;
    }
}
