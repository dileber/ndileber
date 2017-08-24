package com.drcosu.ndileber.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
/**
 * Created by WaTaNaBe on 2017/8/23.
 */

public abstract class LazyFragment extends BaseLazyFragment {

    private boolean isInit = false;
    private Bundle savedInstanceState = null;
    private boolean isLazyLoad = true;
    private FrameLayout layout = null;
    private boolean isStart = false;

    static final class Field {
        final static String INTENT_BOOLEAN_LAZY_LOAD = "INTENT_BOOLEAN_LAZY_LOAD";
    }

    public LazyFragment() {
        super();
    }

    @Override
    protected final void onCreateView(Bundle savedInstanceState) {
        if (getArguments() != null) {
            isLazyLoad = getArguments().getBoolean(LazyFragment.Field.INTENT_BOOLEAN_LAZY_LOAD, isLazyLoad);
        }
        if (isLazyLoad) {
            if (getUserVisibleHint() && !isInit) {
                isInit = true;
                this.savedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);
            } else {
                layout = new FrameLayout(context);
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                super.setContentView(layout);
            }
        } else {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
        }
    }

    /**
     * Generate content view and set it by {@link #setContentView()},</br>
     * or set view by {@link #setContentView()}.
     *
     * @param savedInstanceState from {@link #onCreateView(Bundle)}
     */
    protected abstract void onCreateViewLazy(Bundle savedInstanceState);


    @Override
    protected void setContentView() {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            if (layout != null) {
                layout.removeAllViews();
                View view = inflater.inflate(layoutViewId(), layout, false);
                layout.addView(view);
                lazyInAnimation(view);
            }
        } else {
            super.setContentView();
        }
    }

    /**
     * Set content view to fragment container.<br/>
     * Yur can animate the content view by {@link #lazyInAnimation(View)}
     *
     * @param view
     */
    @Override
    protected final void setContentView(View view) {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            if (layout != null) {
                layout.removeAllViews();
                layout.addView(view);
                lazyInAnimation(view);
            }
        } else {
            super.setContentView(view);
        }
    }

    /**
     * For example:<br/>
     * <br/>
     * {@link android.view.animation.AlphaAnimation#AlphaAnimation(float, float)};<br/>
     * {@link android.view.animation.AlphaAnimation#setDuration(long)};<br/>
     * {@link android.view.animation.AlphaAnimation#setFillAfter(boolean)};<br/>
     * {@link View#startAnimation(Animation)}
     *
     * @param contentView view which will be animated.
     */
    protected void lazyInAnimation(View contentView) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isInit && getContentView() != null) {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            onResumeLazy();
        }

        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onStartLazy();
            } else {
                isStart = false;
                onStopLazy();
            }
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onStartLazy();
        }
    }

    public void onStartLazy() {

    }

    @Override
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    public void onResumeLazy() {

    }

    @Override
    public final void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    public void onPauseLazy() {

    }

    @Override
    public final void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onStopLazy();
        }
    }

    public void onStopLazy() {

    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLay();
        }
        isInit = false;
    }

    public void onDestroyViewLay() {

    }
}
