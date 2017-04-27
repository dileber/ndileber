package com.drcosu.ndileber.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.tools.UUi;
import com.orhanobut.logger.Logger;


/**
 * Created by shidawei on 16/6/2.
 */
public abstract class BaseFragment extends Fragment{

    /**
     * 主要用于缓存view
     * 用 sparseArray 代替hashmap是个性能上不错的选择
     * SparseArray来代替HashMap了，但是要注意SparseArray中的key是int类型\
     */
    @Deprecated
    protected final SparseArray<View> mViews = new SparseArray<View>();

    /**
     * 后期用 findView这个方法替换
     * @param mView
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated
    public <T extends View> T getView(View mView,int id) {
        return UUi.getView(mView,mViews,id);
    }

    protected <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }

    /**
     * 优先使用
     * @return
     */
    protected abstract int layoutViewId();

    /**
     * 多种情况，可以用绑定的view
     * 下一版本将会改造
     * @return
     */
    @Deprecated
    protected abstract View layoutView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 为了能够做绑定，加一个判断
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return initLayout(inflater,container,savedInstanceState);
    }

    protected View initLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = layoutView(inflater,container,savedInstanceState);
        if(layoutViewId()!=0){
            View view =  inflater.inflate(layoutViewId(), container, false);
            return view;
        }else if(layout!=null){
            return layout;
        }else {
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView(),savedInstanceState);
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 后期用initView(Bundle savedInstanceState)来代替
     * @param view
     * @param savedInstanceState
     */
    @Deprecated
    protected abstract void initView(View view, Bundle savedInstanceState);


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示
            hidden();
            Logger.i("hidden");
        } else {// 重新显示到最前端中
            show();
            Logger.i("show");

        }
    }


    /**
     * fragment 显示时候调用的方法
     */
    protected abstract void show();

    /**
     * fragment 隐藏时候调用的方法
     */
    protected abstract void hidden();

    protected OnBaseInteractionListener mBaseListener;

    public interface OnBaseInteractionListener {
        void onRightButtonString(BaseFragment fragment,String str, View.OnClickListener onClickListener);
        void onTitleName(BaseFragment fragment,String title);
    }

    protected void setActivityRightButton(BaseFragment fragment,String str,View.OnClickListener onClickListener) {
        if (mBaseListener != null) {
            mBaseListener.onRightButtonString(fragment,str,onClickListener);
        }
    }

    protected void setActivityTitle(BaseFragment fragment,String title){
        if (mBaseListener != null) {
            mBaseListener.onTitleName(fragment,title);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseListener = null;
    }

    public void setOnClickListener(View.OnClickListener listener, @IdRes int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            findView(id).setOnClickListener(listener);
        }

    }
}
