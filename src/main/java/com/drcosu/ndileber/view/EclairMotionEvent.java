package com.drcosu.ndileber.view;

import android.view.MotionEvent;

/**
 * Created by jing on 2016/8/24.
 */
public class EclairMotionEvent extends WrapMotionEvent {

    protected EclairMotionEvent(MotionEvent event) {
        super(event);
    }

    public float getX(int pointerIndex) {
        return event.getX(pointerIndex);
    }

    public float getY(int pointerIndex) {
        return event.getY(pointerIndex);
    }

    public int getPointerCount() {
        return event.getPointerCount();
    }

    public int getPointerId(int pointerIndex) {
        return event.getPointerId(pointerIndex);
    }
}