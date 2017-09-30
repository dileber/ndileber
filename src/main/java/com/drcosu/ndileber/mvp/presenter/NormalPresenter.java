package com.drcosu.ndileber.mvp.presenter;

import android.support.annotation.NonNull;

import com.drcosu.ndileber.mvp.view.BaseView;

/**
 * Created by WaTaNaBe on 2017/9/30.
 */

public abstract class NormalPresenter <T1 extends BaseView> implements BasePresenter {
    protected T1 mView;

    public NormalPresenter(@NonNull T1 view){
        this.mView = view;
        this.mView.setPresenter(this);
    }

}
