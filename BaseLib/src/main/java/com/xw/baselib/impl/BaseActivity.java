package com.xw.baselib.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xw.baselib.IPresenter;
import com.xw.baselib.IView;

public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppActivityManager.getInstance().addActivity(this);
        setLayout();
        mPresenter = initJector();
        attacheView();
        bindView();
        bindEvent();
        initData();
    }

    public Context getContext(){
        return this;
    }
    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     * 初始化控件
     */
    protected abstract void bindView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * P层绑定View层
     */
    private void attacheView(){
        if (mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    /**
     * 移除绑定
     */
    private void detachView(){
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    /**
     * P层绑定，若无返回null
     * @return
     */
    protected abstract T initJector();

    /**
     * 载入布局
     */
    protected abstract void setLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
        AppActivityManager.getInstance().remove(this);
    }
}
