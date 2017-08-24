package com.drcosu.ndileber.mvp.utils;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by WaTaNaBe on 2017/8/24.
 */

public interface ViewFinder {
    <T extends View> T  findView(@IdRes int var1);

    //<T extends View> T  viewWith(Object var1);

    void setOnClickListener(View.OnClickListener listener, @IdRes int... ids);

    int layoutViewId();

}
