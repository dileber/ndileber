package com.drcosu.ndileber.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by WaTaNaBe on 2017/8/19.
 */

public abstract class DileberBaseView extends LinearLayout{
    public DileberBaseView(Context context) {
        super(context);
    }

    public DileberBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DileberBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

}
