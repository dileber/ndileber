package com.drcosu.ndileber.mvp.presenter;

import android.support.annotation.NonNull;

import com.drcosu.ndileber.mvp.data.BaseDataSource;
import com.drcosu.ndileber.mvp.view.BaseView;

/**
 * Created by shidawei on 2016/9/24.
 */
public abstract class DileberPresenter<T1 extends BaseView,T2 extends BaseDataSource> implements BasePresenter {


    protected T1 mView;
    protected T2 mDataSource;

    public DileberPresenter(@NonNull T1 view, @NonNull T2 mDataSource){
        this.mView = view;
        this.mDataSource = mDataSource;
        this.mView.setPresenter(this);
    }

}
