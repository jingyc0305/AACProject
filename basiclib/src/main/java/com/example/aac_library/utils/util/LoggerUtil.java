package com.example.aac_library.utils.util;

import androidx.annotation.Nullable;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author: JingYuchun
 * @date: 2019/7/25 16:33
 * @desc:
 */
public class LoggerUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    /**
     * 是否开启debug
     */
    private static boolean isDebug = true;

    public static void init(boolean isLogEnable) {
        isDebug = isLogEnable;
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isLogEnable;
            }
        });

    }

    public static void d(String tag, String message) {
        if (isDebug)
            Logger.t(tag).d(message);

    }

    public static void i(String tag, String message) {
        if (isDebug)
            Logger.t(tag).i(message);

    }

    public static void w(String tag, String message, Throwable e) {
        if (isDebug) {
            String info = e != null ? e.toString() : "null";
            Logger.t(tag).w(message + "：" + info);
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (isDebug)
            Logger.t(tag).e(e, message);

    }

    public static void json(String tag, String json) {
        if (isDebug)
            Logger.t(tag).json(json);

    }

}
