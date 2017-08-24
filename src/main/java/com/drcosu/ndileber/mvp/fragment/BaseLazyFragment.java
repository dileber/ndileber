package com.drcosu.ndileber.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.drcosu.ndileber.mvp.utils.OnBaseInteractionListener;
import com.drcosu.ndileber.mvp.utils.ViewFinder;
import com.drcosu.ndileber.tools.UUi;
import com.drcosu.ndileber.tools.log.ULog;

import java.lang.reflect.Field;


public abstract class BaseLazyFragment extends Fragment implements ViewFinder{

    protected LayoutInflater inflater;
    protected View contentViewShadow;
    protected Context context;

    private ViewGroup container;

    public BaseLazyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        return contentViewShadow != null ? contentViewShadow : super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected void setContentView() {
        setContentView(inflater.inflate(layoutViewId(), container, false));
    }

    protected void setContentView(View view) {
        contentViewShadow = view;
    }

    public View getContentView() {
        return contentViewShadow;
    }

    @Override
    public <T extends View> T findView(@IdRes int resId) {
        if (contentViewShadow != null) {
            return  (T) contentViewShadow.findViewById(resId);
        }
        return null;
    }

//    @Override
//    public final <T extends View> T viewWith(Object tag) {
//        if (contentViewShadow != null) {
//            return (T) contentViewShadow.findViewWithTag(tag);
//        }
//        return null;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        container = null;
        contentViewShadow = null;
        inflater = null;
        mBaseListener = null;
    }

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
        try {
            final Field mChildFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            mChildFragmentManager.setAccessible(true);
            mChildFragmentManager.set(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
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