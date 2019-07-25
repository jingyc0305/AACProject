package com.example.aac_library.http.interceptor;

import com.example.aac_library.utils.util.JsonFormatUtil;
import com.example.aac_library.utils.util.LoggerUtil;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author: JingYuchun
 * @date: 2019/7/25 16:35
 * @desc: log日志打印拦截
 */
public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();
    private static final String TAG = "okhttp";
    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonFormatUtil.formatJson(JsonFormatUtil.decodeUnicode(message));
        }
        mMessage.append(message.concat("\n"));
        // 响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LoggerUtil.d(TAG,mMessage.toString());
        }
    }

}
