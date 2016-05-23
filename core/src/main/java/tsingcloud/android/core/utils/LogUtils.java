package tsingcloud.android.core.utils;

import android.text.TextUtils;
import android.util.Log;


/**
 * Created by admin on 2016/3/16.
 * 打印工具类
 */
public class LogUtils {
    public static final boolean DEBUG = true;

    public static void v(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Exception e) {
        if (DEBUG) {
            if (TextUtils.isEmpty(message))
                message = "要打印的信息为null";
            Log.e(tag, message, e);
        }
    }
}
