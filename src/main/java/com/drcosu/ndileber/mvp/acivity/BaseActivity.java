package com.drcosu.ndileber.mvp.acivity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.tools.TKeybord;
import com.drcosu.ndileber.tools.UUi;
import com.drcosu.ndileber.tools.annotation.CheckKeyboard;
import com.drcosu.ndileber.tools.annotation.CloseStatusBar;
import com.drcosu.ndileber.tools.annotation.CloseTitle;
import com.drcosu.ndileber.tools.annotation.HideKeyboard;
import com.drcosu.ndileber.utils.UToolBar;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;


/**
 * 待改进:
 * 继承最新的AppCompatActivity 来做基础activity
 * Created by shidawei on 16/6/2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 主要用于缓存view
     * 用 sparseArray 代替hashmap是个性能上不错的选择
     * SparseArray来代替HashMap了，但是要注意SparseArray中的key是int类型\
     */
    @Deprecated
    protected final SparseArray<View> mViews = new SparseArray<View>();

    /**
     * activity管理工具
     */
    ActivityManager activityManager = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /**
         * 主题采用noactionbar 添加toolsbar 这里废弃
         */
//        if (this.getClass().isAnnotationPresent(CloseTitle.class)) {
//            if(this.getClass().getAnnotation(CloseTitle.class).value()){
//                //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                getSupportActionBar().hide();
//            }
//        }
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
        ActivityManager.setCurrentActivity(this);
        startView(savedInstanceState);
        if(layoutViewId()!=0){
            setContentView(layoutViewId());
        }
        initView(savedInstanceState);
        if (this.getClass().isAnnotationPresent(CheckKeyboard.class)) {
            if(this.getClass().getAnnotation(CheckKeyboard.class).value()){
                checkKeyboard();
            }
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener check_onGloba = null;
    private boolean check_laast = false;

    protected CheckOnGlobaLinstener checkOnGlobaLinstener = null;

    public interface CheckOnGlobaLinstener{
        void showKeyboard();
    }

    /**
     *     检查键盘是否打开
     */
    protected void setCheckOnGlobaLinstener(CheckOnGlobaLinstener checkOnGlobaLinstener) {
        this.checkOnGlobaLinstener = checkOnGlobaLinstener;
    }

    public void checkKeyboard(){
        final View decorView = getWindow().getDecorView();
        check_onGloba = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //计算出可见屏幕的高度
                int displayHight = rect.bottom - rect.top;
                //获得屏幕整体的高度
                int hight = decorView.getHeight();
                boolean visible = (double) displayHight / hight < 0.8;

                if(visible&&visible!= check_laast){
                    checkOnGlobaLinstener.showKeyboard();
                }
                check_laast = visible;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(check_onGloba);
    }

    protected abstract void startView(Bundle savedInstanceState);

    protected abstract int layoutViewId();

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(check_onGloba!=null){
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                decorView.getViewTreeObserver().removeOnGlobalLayoutListener(check_onGloba);
            }else{
                decorView.getViewTreeObserver().removeGlobalOnLayoutListener(check_onGloba);
            }
        }
        /**
         * 将activity从栈中弹出
         */
        activityManager.popActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityManager.clearCurrentActivity(this);
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        activityManager.setCurrentActivity(this);
//    }


    private Toolbar toolbar;

    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    public void setToolBar(int toolbarId, UToolBar uToolBar) {
        toolbar = findView(toolbarId);
        if (uToolBar.titleId != 0) {
            toolbar.setTitle(uToolBar.titleId);
        }
        if (!TextUtils.isEmpty(uToolBar.titleString)) {
            toolbar.setTitle(uToolBar.titleString);
        }
        if (uToolBar.logoId != 0) {
            toolbar.setLogo(uToolBar.logoId);
        }
        if(uToolBar.background !=0){
            toolbar.setBackgroundColor(uToolBar.background);
        }
        setSupportActionBar(toolbar);

        if (uToolBar.isNeedNavigate) {
            toolbar.setNavigationIcon(uToolBar.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigateUpClicked();
                }
            });
        }
    }

    public void onNavigateUpClicked() {
        onBackPressed();
    }

    /**
     * 通过id获取view 后期使用 findView来替换
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated
    public <T extends View> T getView(int id) {
        return UUi.getView(this,mViews,id);
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

    /**
     * 给指定的控件设置监听器
     * @param listener  OnClick监听器
     * @param views    需要被设置监听器的控件，int型的id或者view子类均可
     */
    protected void setOnClickLinstenerToView(View.OnClickListener listener, Object... views){
        for(Object view:views){
            if (view instanceof View){
                ((View)view).setOnClickListener(listener);
            }else if(view instanceof Integer){
                findViewById((Integer) view).setOnClickListener(listener);
            }else {
                throw new RuntimeException("Wrong view object to add View.OnClickListener: \n"+ view.getClass().getName()+" : "+view);
            }
        }
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

    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
    }

    public <A extends Application> A getMyApplication() {
        return (A) getApplication();
    }


}
