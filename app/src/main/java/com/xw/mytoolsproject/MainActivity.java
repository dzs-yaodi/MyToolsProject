package com.xw.mytoolsproject;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xw.baselib.impl.BaseActivity;
import com.xw.mytoolsproject.model.MainPersenterImpl;
import com.xw.mytoolsproject.presenter.MainPresenter;
import com.xw.mytoolsproject.view.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void bindView() {
        ImageView images = findViewById(R.id.images);

        Glide.with(getContext()).load(R.mipmap.image3).into(images);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected MainPresenter initJector() {
        return new MainPersenterImpl(getIntent());
    }

    @Override
    public void upDataView() {

    }

    @Override
    public void error() {

    }
}
