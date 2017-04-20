package com.drcosu.ndileber.mvp.ubase;

import android.app.Dialog;
import android.content.Context;
import com.drcosu.ndileber.mvp.acivity.BaseActivity;
import com.drcosu.ndileber.mvp.view.BView;
import com.drcosu.ndileber.tools.DialogLinstener;
import com.drcosu.ndileber.tools.UDialog;
import com.drcosu.ndileber.tools.UUi;

/**
 * 直接可以使用的默认的Activity
 * Created by shidawei on 2017/4/20.
 */

public abstract class UBaseActivity extends BaseActivity implements BView {


    @Override
    public void toast(String msg, int duration) {
        UUi.toast(this,msg,duration);
    }

    @Override
    public void showAlert(Integer type, String message) {
        UDialog.alert(type,message).show();
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

    @Override
    public void dialogOk(String content, DialogLinstener dialogLinstener) {
        UDialog.dialogOk(content,dialogLinstener).show();
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

    }
}
