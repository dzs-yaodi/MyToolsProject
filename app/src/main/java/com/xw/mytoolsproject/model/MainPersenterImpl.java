package com.xw.mytoolsproject.model;

import android.content.Intent;

import com.xw.baselib.impl.BasePresenterImpl;
import com.xw.mytoolsproject.presenter.MainPresenter;
import com.xw.mytoolsproject.view.IMainView;

public class MainPersenterImpl extends BasePresenterImpl<IMainView> implements MainPresenter {


    /**
     * intent传值可以通过这个方法传递
     * @param intent
     */
    public MainPersenterImpl(Intent intent) {

    }

    @Override
    public void getInfo() {

    }

    @Override
    public void detachView() {

    }
}
