package com.drcosu.ndileber.tools.debug;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WaTaNaBe on 2017/11/28.
 */

public class DebugView extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener {
    private WindowManager wm;
    private WindowManager.LayoutParams wlp;
    private float x;
    private float y;
    private float newX;
    private float newY;
    private boolean isRelease;
    private boolean isLongPress;
    private final long minTime = 300;
    private Context mContext;
    private boolean isAdded;
    private DebugViewClickLinsenter mDebugViewClickLinsenter;
    Runnable run = new Runnable() {
        @Override
        public void run() {
            //短按
            if(isRelease){
                onClick(DebugView.this);
                return;
            }
            //长按
            isLongPress = true;
        }
    };

    public interface DebugViewClickLinsenter{
        void click(View v);
    }

    public void setmDebugViewClickLinsenter(DebugViewClickLinsenter mDebugViewClickLinsenter) {
        this.mDebugViewClickLinsenter = mDebugViewClickLinsenter;
    }

    public DebugView(Context context) {
        super(context);
        mContext = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wlp = new WindowManager.LayoutParams();
        setOnClickListener(this);
    }

    public DebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DebugView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        x=event.getRawX();
        y=event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isRelease = false;
                newX=event.getX();
                newY=event.getY();
                //300ms后检测 如果没有抬起手认为是长按
                postDelayed(run,minTime);
                break;
            case MotionEvent.ACTION_MOVE:
                if(isLongPress){
                    update();
                }
                break;
            case MotionEvent.ACTION_UP:
                //标记已经抬起手
                isRelease = true;
                if (isLongPress) {
                    isLongPress = false;
                }
                break;

        }
        return true;
    }

    private void update() {
        if(wlp==null){
            return;
        }
        //取view左上角坐标
        wlp.x = (int)(x-newX);
        wlp.y=(int)(y-newY);
        wm.updateViewLayout(this,wlp);
    }


    @Override
    public void onClick(View v) {
        if(mDebugViewClickLinsenter!=null){
            mDebugViewClickLinsenter.click(v);
        }
    }
    public void show(){
        //如果windowmanager已经添加过，则不处理
        if(isAdded){
            return;
        }
        isAdded = true;
        wlp.gravity= Gravity.LEFT| Gravity.TOP;
        wlp.width = 200;
        wlp.height = 200;
        wlp.x=0;
        wlp.y=0;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM|
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if(Build.VERSION.SDK_INT > 24) {
            wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }else {
            wlp.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        wm.addView(this,wlp);
    }
    public void dismiss(){
        if(isAdded) {
            isAdded = false;
            wm.removeView(this);
        }
    }
}