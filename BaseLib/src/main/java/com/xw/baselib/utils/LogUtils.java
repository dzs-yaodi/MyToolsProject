package com.xw.baselib.utils;

import android.util.Log;

import com.xw.baselib.BuildConfig;

/**
 * 日志打印封装
 */
public class LogUtils {

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static String creatLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("=========");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")==========:");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, creatLog(msg));
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(className, creatLog(msg));
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, creatLog(msg));
        }
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.v(className, creatLog(msg));
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(className, creatLog(msg));
        }
    }

}