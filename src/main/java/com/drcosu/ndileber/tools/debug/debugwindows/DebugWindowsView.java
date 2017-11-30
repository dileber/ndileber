package com.drcosu.ndileber.tools.debug.debugwindows;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by feifan on 2017/8/21.
 * Contacts me:404619986@qq.com
 */

public class DebugWindowsView extends LinearLayout {
    private onTouchHandler mTouchHandler;
    private static float lastX;
    private static float lastY;

    public DebugWindowsView(Context context) {
        this(context, null);
    }

    public DebugWindowsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DebugWindowsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                lastY = event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE: {
                float moveX = event.getRawX() - lastX;
                float moveY = event.getRawY() - lastY;
                lastX = event.getRawX();
                lastY = event.getRawY();
                if (mTouchHandler != null) {
                    mTouchHandler.onMove(moveX, moveY);
                }
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (mTouchHandler != null) {
                    mTouchHandler.onMoveEnd();
                }
            }
        }
        return false;
    }

    public void setOnTouchHandler(onTouchHandler touchHandler) {
        mTouchHandler = touchHandler;
    }

    interface onTouchHandler {
        void onMove(float moveX, float moveY);

        void onMoveEnd();
    }
}
