package com.xw.baselib.http;

import com.bumptech.glide.load.HttpException;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public class NetDataUtil {

    /**
     * 将传入的异常转化成友好的提示并返回.
     *
     * @param throwable 异常
     * @return 需要显示的提示
     */
    public static String getMessage(Throwable throwable) {
        String message;
        if (throwable instanceof HttpException) {
            message = throwable.getMessage();
        } else if (throwable instanceof DataException) {
            message = ((DataException) throwable).getErrorMsg();
        } else if (throwable instanceof SocketTimeoutException) {
            message = "连接超时";
        } else if (throwable instanceof ConnectException) {
            message = "连接失败";
        } else if (throwable instanceof JsonSyntaxException || throwable instanceof UnsupportedOperationException) {
            message = "数据异常";
        } else {
            message = "网络开小差了";
        }
        return message;
    }

    /**
     * 返回错误码
     *
     * @param throwable 网络异常类型
     * @return 接口定义的错误码
     */
    public static int getErrorCode(Throwable throwable) {
        if (throwable instanceof DataException) {
            return ((DataException) throwable).getErrorCode();
        } else {
            return -1;
        }
    }

}
