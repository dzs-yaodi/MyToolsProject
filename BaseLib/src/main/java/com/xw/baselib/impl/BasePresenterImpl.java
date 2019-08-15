package com.xw.baselib.impl;

import android.support.annotation.NonNull;

import com.xw.baselib.IPresenter;
import com.xw.baselib.IView;

public abstract class BasePresenterImpl<T extends IView> implements IPresenter{

    protected T mView;

    @Override
    public void attachView(@NonNull IView iView) {

        mView = (T) iView;
    }
}
