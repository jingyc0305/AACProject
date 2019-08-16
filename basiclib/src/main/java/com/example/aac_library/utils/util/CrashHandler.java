package com.example.aac_library.utils.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.blankj.utilcode.util.*;
import com.example.aac_library.R;
import com.example.aac_library.base.BaseApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author: JingYuchun
 * @date: 2019/8/14 18:03
 * @desc:
 */
public class CrashHandler {
    private static String defaultDir;
    private static String dir;
    private static String versionName;
    private static int versionCode;

    private static final String FILE_SEP = System.getProperty("file.separator");
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;

    private static OnCrashListener sOnCrashListener;

    static {
        try {
            PackageInfo pi = Utils.getApp()
                    .getPackageManager()
                    .getPackageInfo(Utils.getApp().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

        UNCAUGHT_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                if (!handlerExecption(e) && DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    Log.e("CrashUtils", "e == null");
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, null);
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Log.e("CrashUtils", "error : ", ex);
                    }
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }
        };
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    private static boolean handlerExecption(Throwable e) {
        if (e == null) {
            return false;
        }
        saveLogFile(e);

        ThreadUtils.getFixedPool(1).submit(() -> {
            Looper.prepare();
            ToastUtil toastUtil = new ToastUtil(BaseApp.getAppContext(), LayoutInflater.from(BaseApp.getAppContext()).inflate(R.layout.crash_layout, null), Toast.LENGTH_LONG);
            toastUtil.setGravity(Gravity.CENTER);
            toastUtil.show();
            Looper.loop();
        });

        return true;
    }

    /**
     * 保存日志文件
     */
    private static void saveLogFile(Throwable e) {
        final String time = FORMAT.format(new Date(System.currentTimeMillis()));
        final StringBuilder sb = new StringBuilder();
        final String head = "************* Log Head ****************" +
                "\nTime Of Crash      : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        sb.append(head)
                .append(ThrowableUtils.getFullStackTrace(e));
        final String crashInfo = sb.toString();
        final String fullPath = (dir == null ? defaultDir : dir) + time + ".txt";
        if (createOrExistsFile(fullPath)) {
            Log.e("CrashUtils", "====input2File====");
            input2File(crashInfo, fullPath);
        } else {
            Log.e("CrashUtils", "create " + fullPath + " failed!");
        }

        if (sOnCrashListener != null) {
            Log.e("CrashUtils", "====onCrash====");
            sOnCrashListener.onCrash(crashInfo, time + ".txt");
        }
    }


    private CrashHandler() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Initialization.
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     */
    @SuppressLint("MissingPermission")
    public static void init() {
        init("");
    }

    /**
     * Initialization
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir The directory of saving crash information.
     */
    @RequiresPermission(WRITE_EXTERNAL_STORAGE)
    public static void init(@NonNull final File crashDir) {
        init(crashDir.getAbsolutePath(), null);
    }

    /**
     * Initialization
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDirPath The directory's path of saving crash information.
     */
    @RequiresPermission(WRITE_EXTERNAL_STORAGE)
    public static void init(final String crashDirPath) {
        init(crashDirPath, null);
    }

    /**
     * Initialization
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param onCrashListener The crash listener.
     */
    @SuppressLint("MissingPermission")
    public static void init(final OnCrashListener onCrashListener) {
        init("", onCrashListener);
    }

    /**
     * Initialization
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDir        The directory of saving crash information.
     * @param onCrashListener The crash listener.
     */
    @RequiresPermission(WRITE_EXTERNAL_STORAGE)
    public static void init(@NonNull final File crashDir, final OnCrashListener onCrashListener) {
        init(crashDir.getAbsolutePath(), onCrashListener);
    }

    /**
     * Initialization
     * <p>Must hold {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />}</p>
     *
     * @param crashDirPath    The directory's path of saving crash information.
     * @param onCrashListener The crash listener.
     */
    @RequiresPermission(WRITE_EXTERNAL_STORAGE)
    public static void init(final String crashDirPath, final OnCrashListener onCrashListener) {
        if (isSpace(crashDirPath)) {
            dir = null;
        } else {
            dir = crashDirPath.endsWith(FILE_SEP) ? crashDirPath : crashDirPath + FILE_SEP;
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Utils.getApp().getExternalCacheDir() != null) {
            defaultDir = Utils.getApp().getExternalCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        } else {
            defaultDir = Utils.getApp().getCacheDir() + FILE_SEP + "crash" + FILE_SEP;
        }
        sOnCrashListener = onCrashListener;
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public interface OnCrashListener {

        /**
         * crash回调
         *
         * @param crashInfo
         * @param path
         */
        void onCrash(String crashInfo, String path);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static void input2File(final String input, final String filePath) {
        Future<Boolean> submit = ThreadUtils.getIoPool().submit(() -> {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write(input);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            if (submit.get()) {
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("CrashUtils", "write crash info to " + filePath + " failed!");
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

