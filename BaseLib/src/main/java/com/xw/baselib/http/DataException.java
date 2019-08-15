package com.xw.baselib.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DataException extends RuntimeException {

    private int errorCode;
    private String errorMsg;
    private String json;

    public DataException(int errorCode, @NonNull String errorMsg, @Nullable String json) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.json = json;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Nullable
    public String getJson() {
        return json;
    }

}
