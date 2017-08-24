package com.drcosu.ndileber.mvp.utils;

import android.view.View;

import com.drcosu.ndileber.mvp.fragment.BaseFragment;

/**
 * Created by WaTaNaBe on 2017/8/24.
 */

public interface OnBaseInteractionListener {
    void onRightButtonString(BaseFragment fragment, String str, View.OnClickListener onClickListener);
    void onTitleName(BaseFragment fragment,String title);
}
