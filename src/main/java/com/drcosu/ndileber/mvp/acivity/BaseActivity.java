package com.drcosu.ndileber.mvp.acivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.tools.UUi;
import com.drcosu.ndileber.tools.annotation.CloseStatusBar;
import com.drcosu.ndileber.tools.annotation.CloseTitle;
import com.drcosu.ndileber.tools.annotation.HideKeyboard;
import com.orhanobut.logger.Logger;


/**
 * 待改进:
 * 继承最新的AppCompatActivity 来做基础activity
 * Created by shidawei on 16/6/2.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 主要用于缓存view
     * 用 sparseArray 代替hashmap是个性能上不错的选择
     * SparseArray来代替HashMap了，但是要注意SparseArray中的key是int类型\
     */
    protected final SparseArray<View> mViews = new SparseArray<View>();

    /**
     * activity管理工具
     */
    ActivityManager activityManager = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(CloseTitle.class)) {
            if(this.getClass().getAnnotation(CloseTitle.class).value()){
                //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getSupportActionBar().hide();
            }
        }
        if (this.getClass().isAnnotationPresent(CloseStatusBar.class)) {
            if(this.getClass().getAnnotation(CloseStatusBar.class).value()){
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        if (this.getClass().isAnnotationPresent(HideKeyboard.class)) {
            hideKeyboardWhenTouchOutside = this.getClass().getAnnotation(HideKeyboard.class).value();
        }
        /**
         * 将activity 添加到activity栈中
         */
        activityManager.pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 将activity从栈中弹出
         */
        activityManager.popActivity(this);
        /**
         * 如果全部弹出则调用退出函数
         */
        if(activityManager.activityInStack()==0){
            SApplication.getInstance().quit();
        }
    }

    /**
     * 通过id获取view
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
        return UUi.getView(this,mViews,id);
    }

    /**
     * 键盘点击消失
     */
    protected boolean hideKeyboardWhenTouchOutside = false;

    /**
     * 点击外部隐藏软键盘的函数
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!hideKeyboardWhenTouchOutside) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
