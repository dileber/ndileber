package com.drcosu.ndileber.mvp.presenter;

import android.support.annotation.NonNull;

import com.drcosu.ndileber.mvp.data.BaseDataSource;
import com.drcosu.ndileber.mvp.view.BaseView;

/**
 * Created by shidawei on 2016/9/24.
 */
public abstract class DileberPresenter<T1 extends BaseView,T2 extends BaseDataSource> implements BasePresenter {


    T1 mView;
    T2 mUserDataSource;

    public DileberPresenter(@NonNull T1 view, @NonNull T2 userDataSource){
        mView = view;
        mUserDataSource = userDataSource;
        mView.setPresenter(this);
    }

}
