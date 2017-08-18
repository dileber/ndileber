package com.drcosu.ndileber.tools.android;

import android.view.View;

/**
 * Created by WaTaNaBe on 2017/8/18.
 */

public abstract class OnOnceClickListener implements View.OnClickListener {
    private long lastClickTime = 0L;
    private final int MIN_CLICK_DELAY_TIME = 500;

    public OnOnceClickListener() {
    }

    public void onClick(View v) {
        long currentClickTime = System.currentTimeMillis();
        if(currentClickTime - this.lastClickTime > 500L) {
            this.onOnceClick(v);
        }

        this.lastClickTime = currentClickTime;
    }

    public abstract void onOnceClick(View var1);
}

