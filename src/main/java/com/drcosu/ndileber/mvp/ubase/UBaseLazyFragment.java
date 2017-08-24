package com.drcosu.ndileber.mvp.ubase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.mvp.fragment.LazyFragment;
import com.drcosu.ndileber.mvp.presenter.BasePresenter;
import com.drcosu.ndileber.mvp.utils.OnBaseInteractionListener;
import com.drcosu.ndileber.mvp.view.BView;
import com.drcosu.ndileber.tools.DialogLinstener;

/**
 * Created by WaTaNaBe on 2017/8/24.
 */

public abstract class UBaseLazyFragment extends LazyFragment implements BView {
    protected BasePresenter presenter;

    @Override
    public void toast(String msg, int duration) {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).toast(msg, duration);
        }
    }

    @Override
    public void showAlert(Integer type, String message) {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).showAlert(type, message);
        }
    }


    @Override
    public void loading() {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).loading();
        }
    }

    @Override
    public void loadDialogDismiss() {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).loadDialogDismiss();
        }
    }

    @Override
    public void dialogOk(String content, DialogLinstener dialogLinstener) {
        if(getActivity() instanceof BView){
            ((BView)getActivity()).dialogOk(content, dialogLinstener);
        }
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
    }

    /**
     * 将presenter设置进来，让父类管理生命周期
     * @param presenter
     */
    protected void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBaseInteractionListener) {
            mBaseListener = (OnBaseInteractionListener) context;
        }
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}

