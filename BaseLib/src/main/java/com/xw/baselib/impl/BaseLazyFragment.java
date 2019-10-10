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

/**
 * 懒加载Fragment
 * @param <T>
 */
public abstract class BaseLazyFragment<T extends IPresenter> extends Fragment implements IView {

    //Fragment对用户可见的标记
    protected boolean isUIVisible;

    //是否执行了onActivityCreated
    private boolean isActivityCreated;

    protected View mView;
    protected T mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isActivityCreated = true;
        lazyInit();
    }

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isUIVisible = true;
            lazyInit();
        }else{
            isUIVisible = false;
        }
    }

    /**
     * 延迟加载
     */
    private void lazyInit() {
        if (isActivityCreated && isUIVisible) {
            loadLazyData();
            //数据加载完毕,恢复标记,防止重复加载
            isActivityCreated = false;
            isUIVisible = false;
        }
    }

    /**
     *延迟加载数据
     */
    protected abstract void loadLazyData();
}
