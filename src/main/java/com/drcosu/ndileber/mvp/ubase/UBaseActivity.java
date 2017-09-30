package com.drcosu.ndileber.mvp.ubase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.drcosu.ndileber.mvp.acivity.BaseActivity;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;
import com.drcosu.ndileber.mvp.view.BView;
import com.drcosu.ndileber.mvp.view.BaseView;
import com.drcosu.ndileber.tools.DialogLinstener;
import com.drcosu.ndileber.tools.UDialog;
import com.drcosu.ndileber.tools.UUi;

/**
 * 直接可以使用的默认的Activity
 * Created by shidawei on 2017/4/20.
 */

public abstract class UBaseActivity<T extends BasePresenter> extends BaseActivity implements BaseView<T> {


    @Override
    public void toast(String msg, int duration) {
        UUi.toast(this,msg,duration);
    }

    Dialog alert;

    @Override
    public void showAlert(Integer type, String message) {
        alert = UDialog.alert(this,type,message);
        alert.show();
    }

    Dialog dialog;

    @Override
    public void loading() {
        if(dialog==null){
            dialog =UDialog.loading();
        }
        dialog.show();
    }

    @Override
    public void loadDialogDismiss() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    Dialog dialogok;

    @Override
    public void dialogOk(String content, DialogLinstener dialogLinstener) {
        dialogok = UDialog.dialogOk(content,dialogLinstener);
        dialogok.show();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.dismiss();
        }
        if(alert!=null){
            alert.dismiss();
        }
        if(dialogok!=null){
            dialogok.dismiss();
        }
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    protected T mPresenter;

    @Override
    public void setPresenter(T presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void startView(Bundle savedInstanceState) {
        mPresenter = this.createPresenterInstance();
    }

    protected abstract T createPresenterInstance();

}
