package com.drcosu.ndileber.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drcosu.ndileber.mvp.utils.OnBaseInteractionListener;
import com.drcosu.ndileber.mvp.utils.ViewFinder;
import com.drcosu.ndileber.tools.UUi;
import com.drcosu.ndileber.tools.log.ULog;;


/**
 * Created by shidawei on 16/6/2.
 */
public abstract class BaseFragment extends Fragment implements ViewFinder{

    /**
     * 主要用于缓存view
     * 用 sparseArray 代替hashmap是个性能上不错的选择
     * SparseArray来代替HashMap了，但是要注意SparseArray中的key是int类型\
     */
    @Deprecated
    protected final SparseArray<View> mViews = new SparseArray<View>();

    @Override
    public <T extends View> T findView(int resId) {
        return (T) (getView().findViewById(resId));
    }


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
        View view =  inflater.inflate(layoutViewId(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    protected OnBaseInteractionListener mBaseListener;

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

    @Override
    public void setOnClickListener(View.OnClickListener listener, @IdRes int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            findView(id).setOnClickListener(listener);
        }
    }
}
