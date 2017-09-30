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
import com.drcosu.ndileber.mvp.view.BaseView;
import com.drcosu.ndileber.tools.DialogLinstener;

/**
 * Created by WaTaNaBe on 2017/8/24.
 */

public abstract class UBaseLazyFragment<T extends BasePresenter>  extends LazyFragment implements BaseView<T> {

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
    public void onResumeLazy() {
        super.onResumeLazy();
        if(mPresenter!=null){
            mPresenter.onResume();
        }
    }

    @Override
    public void onPauseLazy() {
        super.onPauseLazy();
        if(mPresenter!=null){
            mPresenter.onPause();
        }
    }
    protected T mPresenter;

    /**
     * 将presenter设置进来，让父类管理生命周期
     * @param presenter
     */
    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }


//    protected void setPresenter(BasePresenter presenter) {
//        this.presenter = presenter;
//    }
    protected abstract T createPresenterInstance();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = this.createPresenterInstance();
        if (context instanceof OnBaseInteractionListener) {
            mBaseListener = (OnBaseInteractionListener) context;
        }
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }
}

