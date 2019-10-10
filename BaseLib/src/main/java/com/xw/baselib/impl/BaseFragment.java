package com.xw.baselib.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xw.baselib.IPresenter;
import com.xw.baselib.IView;

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    protected View mView;
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = createView(inflater,container);
        mPresenter = initJector();
        bindView();
        bindEvent();
        initData();

        return mView;
    }

    /**
     * P层绑定，若无返回null
     * @return
     */
    protected abstract T initJector();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化控件
     */
    protected abstract void bindView();

    /**
     * 加载布局
     * @param inflater
     * @param container
     * @return
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);
}
