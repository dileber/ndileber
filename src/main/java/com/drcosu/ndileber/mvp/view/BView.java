package com.drcosu.ndileber.mvp.view;

import android.content.Context;

import com.drcosu.ndileber.tools.DialogLinstener;

/**
 * Created by shidawei on 16/9/19.
 */
public interface BView {
    /**
     * toast 能够弹出自定义toast
     * @param msg
     * @param duration
     */
    void toast(String msg,int duration);

    /**
     * 设置不同类型的弹出框
     * @param type
     * @param message
     */
    void showAlert(Integer type,String message);

    /**
     * 加载中
     */
    void loading();

    /**
     * 取消加载
     */
    void loadDialogDismiss();

    /**
     * 弹出确定框子
     * @param content
     * @param dialogLinstener
     */
    void dialogOk(String content, final DialogLinstener dialogLinstener);

    @Deprecated
    Context getContext();
}
