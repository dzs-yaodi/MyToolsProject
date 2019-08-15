package com.xw.baselib;

import android.support.annotation.NonNull;

public interface IPresenter {

    /**
     * 注入View，使之能够与View相互响应
     */

    void attachView(@NonNull IView iView);


    /**
     * 释放资源
     */

    void detachView();
}
