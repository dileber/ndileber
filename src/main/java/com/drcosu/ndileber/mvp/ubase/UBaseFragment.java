package com.drcosu.ndileber.mvp.ubase;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.drcosu.ndileber.mvp.fragment.BaseFragment;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;
import com.drcosu.ndileber.mvp.view.BView;
import com.drcosu.ndileber.tools.DialogLinstener;
import com.drcosu.ndileber.tools.UDialog;
import com.drcosu.ndileber.tools.UUi;
import com.orhanobut.logger.Logger;

/**
 * 直接可以使用的默认的fragment
 * Created by shidawei on 2017/4/20.
 */

public abstract class UBaseFragment extends BaseFragment implements BView {
    protected BasePresenter presenter;

    @Override
    public void toast(String msg, int duration) {
        UUi.toast(getActivity(),msg,duration);
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
        UDialog.dialogOk(content, dialogLinstener);
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter!=null){
            presenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.onDestroy();
        }
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    /**
     * 将presenter设置进来，让父类管理生命周期
     * @param presenter
     */
    protected void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

}
