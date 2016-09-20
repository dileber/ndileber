package com.drcosu.ndileber.mvp.view;

import android.content.Context;

import com.drcosu.ndileber.tools.DialogLinstener;

/**
 * Created by shidawei on 16/6/2.
 */
public interface BaseView<T> extends BView{

    /**
     * 设置presenter
     * @param presenter
     */
    void setPresenter(T presenter);



}
