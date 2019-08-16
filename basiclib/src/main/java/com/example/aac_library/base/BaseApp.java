package com.example.aac_library.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import com.blankj.utilcode.util.*;
import com.example.aac_library.BuildConfig;
import com.example.aac_library.utils.util.*;
import com.sunchen.netbus.NetStatusBus;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BaseApp extends Application {

    private static BaseApp appContext;
    private int activityCount = 0;
    /**
     * 维护Activity 的list
     */
    private static List<Activity> mActivitys = Collections
            .synchronizedList(new LinkedList<>());
    private boolean isAppBackGround;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        NetStatusBus.getInstance().init(this);
        registerActivityLifecycleCallbacks(callbacks);

        // 初始化 Looger工具
        LoggerUtil.init(BuildConfig.DEBUG);
        // 初始化 崩溃捕获
        CrashHandler.init(this::uploadCrashLogString);

        //CrashHandler2 crashHandler = CrashHandler2.getInstance();

        //crashHandler.init(this);
    }

    /**
     * 直接上传log字符串流
     */
    public void uploadCrashLogString(String crashInfo, String filename) {
        FTPUtils.getInstance().initFtpClient();
        ThreadUtils.getIoPool().submit(() -> {
            String saveFilePath = "developer/crash/" + AppUtils.getAppPackageName() + System.getProperty("file.separator");
            Log.e("CrashUtils","saveFilePath="+saveFilePath+"   filename="+filename);
            FTPUtils.getInstance().uploadFile(saveFilePath, filename, ConvertUtils.string2InputStream(crashInfo, "UTF-8"));
        });
    }

    /**
     * 启动程序后上传 缓存log 文件
     * 默认上传最后一次崩溃保存的文件
     */
    private void uploadCrashLogFile() {
        FTPUtils.getInstance().initFtpClient();
        List<File> list = FileUtils.listFilesInDir(Utils.getApp().getExternalCacheDir() + System.getProperty("file.separator") + "crash" + System.getProperty("file.separator"));
        ThreadUtils.getIoPool().submit(() -> {
            /**
             * 定义ftp服务器存储路径
             */
            String saveFilePath = "developer/crash/" + AppUtils.getAppPackageName() + System.getProperty("file.separator");
            /**
             * 异常log日志上传
             */
            FTPUtils.getInstance().uploadFile(saveFilePath, FileUtils.getFileName(list.get(list.size()-1)), list.get(list.size()-1));
        });
    }

    public static BaseApp getAppContext() {
        return appContext;
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
        LogUtils.d("activityList:size:" + mActivitys.size());
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        mActivitys.remove(activity);
        LogUtils.d("activityList:size:" + mActivitys.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return null;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        Activity activity = mActivitys.get(mActivitys.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            mActivitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mActivitys != null) {
            for (Activity activity : mActivitys) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mActivitys) {
            final int size = mActivitys.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mActivitys.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivitys == null) {
            return;
        }
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        mActivitys.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            LogUtils.e("app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    public boolean isAppBackGround() {
        return isAppBackGround;
    }

    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activityCount++;
            pushActivity(activity);

            if (activityCount == 1) {
                isAppBackGround = false;
            }
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
            if (null == mActivitys || mActivitys.isEmpty()) {
                return;
            }
            if (mActivitys.contains(activity)) {
                /**
                 *  监听到 Activity销毁事件 将该Activity 从list中移除
                 */
                popActivity(activity);
            }
            if (activityCount == 0) {
                isAppBackGround = true;
            }
        }
    };

}
