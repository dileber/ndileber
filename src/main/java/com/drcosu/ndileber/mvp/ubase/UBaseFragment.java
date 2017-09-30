package com.drcosu.ndileber.mvp.ubase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drcosu.ndileber.mvp.fragment.BaseFragment;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;
import com.drcosu.ndileber.mvp.utils.OnBaseInteractionListener;
import com.drcosu.ndileber.mvp.view.BView;
import com.drcosu.ndileber.mvp.view.BaseView;
import com.drcosu.ndileber.tools.DialogLinstener;
import com.drcosu.ndileber.tools.log.ULog;

/**
 * 直接可以使用的默认的fragment 使用UBaseFragment 必须使用 UBaseActivity或者继承 BaseActivity 实现 BView
 * Created by shidawei on 2017/4/20.
 */

public abstract class UBaseFragment<T extends BasePresenter> extends BaseFragment implements BaseView<T> {

    @Override
    public void toast(String msg, int duration) {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).toast(msg, duration);
        }
        //UUi.toast(getActivity(),msg,duration);
    }

    //Dialog alert;

    @Override
    public void showAlert(Integer type, String message) {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).showAlert(type, message);
        }
//        alert = UDialog.alert(getActivity(),type,message);
//        alert.show();
    }

//    Dialog dialog;

    @Override
    public void loading() {
//        if(dialog==null){
//            dialog =UDialog.loading(getActivity());
//        }
//        dialog.show();
        if(getActivity() instanceof BView){
            ((BView)getActivity()).loading();
        }
    }

    @Override
    public void loadDialogDismiss() {
//        if(dialog!=null){
//            dialog.dismiss();
//        }
        if(getActivity() instanceof BView){
            ((BView)getActivity()).loadDialogDismiss();
        }
    }

    protected abstract boolean retain();

    @Override
    protected View initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(layoutViewId(), container, false);
        if(retain()){
            setRetainInstance(retain());
        }
        return view;
    }

//    Dialog dialogok;

    @Override
    public void dialogOk(String content, DialogLinstener dialogLinstener) {
//        dialogok = UDialog.dialogOk(content, dialogLinstener);
//        dialogok.show();
        if(getActivity() instanceof BView){
            ((BView)getActivity()).dialogOk(content, dialogLinstener);
        }
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }


    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mPresenter!=null){
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter!=null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPresenter!=null){
            mPresenter.onPause();
        }
    }

    /**
     * 将presenter设置进来，让父类管理生命周期
     * @param presenter
     */
    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = this.createPresenterInstance();
        if (context instanceof OnBaseInteractionListener) {
            mBaseListener = (OnBaseInteractionListener) context;
        }
    }

    protected abstract T createPresenterInstance();

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}
