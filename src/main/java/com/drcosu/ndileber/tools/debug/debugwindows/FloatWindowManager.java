package com.drcosu.ndileber.tools.debug.debugwindows;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.drcosu.ndileber.R;
import com.drcosu.ndileber.app.BaseConfiger;
import com.drcosu.ndileber.app.SApplication;


import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
import static android.view.WindowManager.LayoutParams.TYPE_TOAST;

/**
 * Created by feifan on 2017/8/21.
 * Contacts me:404619986@qq.com
 */

public class FloatWindowManager {
//    @SuppressLint("StaticFieldLeak")
//    private WindowManager mWindowManager;
//    private Context mContext;
//
//    //小窗布局相关
//    private View mSmallWindow;
//    private WindowManager.LayoutParams mSmallWindowParams;
//    private DisplayMetrics mDisplayMetrics;
//    private int mVideoViewWidth;
//    private int mVideoViewHeight;
//    private int mStatusBarHeight;
//    private int mNavigationBarHeight;
//    private int dp12;
//    private boolean isAddToWindow = false;
//
//    private FloatWindowManager() {
//        mContext = SApplication.getAppContext();
//        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        mStatusBarHeight = SystemBarUtils.getStatusBarHeight(mContext);
//        mNavigationBarHeight = SystemBarUtils.getNavigationBarHeight(mContext);
//        createSmallWindow();
//    }
//    private static FloatWindowManager sInstance = new FloatWindowManager();
//
//    public static FloatWindowManager getInstance() {
//        return sInstance;
//    }
//
//     Context activity;
//
//    public void showFloatWindow(Context activity) {
//        if(BaseConfiger.BUG_STATIC){
//            this.activity = activity;
//
//            if (!FloatWindowPermissionChecker.checkFloatWindowPermission()) {
//                FloatWindowPermissionChecker.askForFloatWindowPermission(activity);
//                return;
//            }
//            if (isAddToWindow) return;
//            try {
//                mWindowManager.addView(mSmallWindow, mSmallWindowParams);
//            } catch (Exception e) {
//                mWindowManager.updateViewLayout(mSmallWindow, mSmallWindowParams);
//            }
//            isAddToWindow = true;
//        }
//    }
//
//    public void logFloatWindow(String message){
//        if(BaseConfiger.BUG_STATIC){
//
//            Message msg1 = mHandler.obtainMessage();
//            msg1.arg1 = 0;
//            msg1.obj = message+"\n";
//            mHandler.sendMessage(msg1);
//
//        }
//    }
//
//    Handler mHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    String data = (String)msg.obj;
//                    if(logView!=null){
//                        logView.append(data);
//                        int offset=logView.getLineCount()*logView.getLineHeight();
//                        if(offset>logView.getHeight()){
//                            logView.scrollTo(0,offset-logView.getHeight());
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    };
//
//
//    public void removeFromWindow() {
//        if(BaseConfiger.BUG_STATIC){
//            if (!isAddToWindow) return;
//            mWindowManager.removeView(mSmallWindow);
//            isAddToWindow = false;
//        }
//    }
//    TextView logView;
//    private void createSmallWindow() {
//        mSmallWindow = LayoutInflater.from(mContext).inflate(R.layout.layout_debug_window, null);
//        final DebugWindowsView fakeVideoView = (DebugWindowsView) mSmallWindow.findViewById(R.id.debug);
//        logView = (TextView) mSmallWindow.findViewById(R.id.debugLog);
//        logView.setMovementMethod(ScrollingMovementMethod.getInstance());
//        fakeVideoView.setOnTouchHandler(new DebugWindowsView.onTouchHandler() {
//            @Override
//            public void onMove(float moveX, float moveY) {
//                mSmallWindowParams.x += moveX;
//                mSmallWindowParams.y += moveY;
//                if (isAddToWindow) {
//                    mWindowManager.updateViewLayout(mSmallWindow, mSmallWindowParams);
//                }
//            }
//
//            @Override
//            public void onMoveEnd() {
//                final int currentX = mSmallWindowParams.x;
//                final int currentY = mSmallWindowParams.y;
//                int dx = 0;
//                int dy = 0;
//                if (mSmallWindowParams.x > mDisplayMetrics.widthPixels - mVideoViewWidth) {
//                    dx = (mDisplayMetrics.widthPixels - mVideoViewWidth) - mSmallWindowParams.x;
//                } else if (mSmallWindowParams.x < 0) {
//                    dx = -mSmallWindowParams.x;
//                }
//                if (mSmallWindowParams.y > mDisplayMetrics.heightPixels - mVideoViewHeight - mStatusBarHeight) {
//                    dy = (mDisplayMetrics.heightPixels - mVideoViewHeight - mStatusBarHeight) - mSmallWindowParams.y;
//                } else if (mSmallWindowParams.y < 0) {
//                    dy = -mSmallWindowParams.y;
//                }
//                if (dx == 0 && dy == 0) {
//                    return;
//                }
//                ValueAnimator x = ValueAnimator.ofInt(0, dx).setDuration(300);
//                x.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mSmallWindowParams.x = currentX + (int) animation.getAnimatedValue();
//                    }
//                });
//                ValueAnimator y = ValueAnimator.ofInt(0, dy).setDuration(300);
//                y.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        mSmallWindowParams.y = currentY + (int) animation.getAnimatedValue();
//                        if (isAddToWindow) {
//                            mWindowManager.updateViewLayout(mSmallWindow, mSmallWindowParams);
//                        }
//                    }
//                });
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(x).with(y);
//                animatorSet.start();
//            }
//        });
//        View close =  mSmallWindow.findViewById(R.id.close);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logView.setText("");
//            }
//        });
//        close.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                removeFromWindow();
//                return true;
//            }
//        });
//
//        mSmallWindow.findViewById(R.id.big).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
//
//        ViewGroup.LayoutParams videoLayoutParams = fakeVideoView.getLayoutParams();
//        mDisplayMetrics = Resources.getSystem().getDisplayMetrics();
//        mVideoViewWidth = (int) (mDisplayMetrics.widthPixels * 0.65);
//        mVideoViewHeight = (int) (mVideoViewWidth / 16.0 * 9.0) + 1;
//        videoLayoutParams.width = mVideoViewWidth;
//        videoLayoutParams.height = mVideoViewHeight;
//        fakeVideoView.setLayoutParams(videoLayoutParams);
//
//        dp12 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics);
//        mSmallWindowParams = new WindowManager.LayoutParams();
//        mSmallWindowParams.width = WRAP_CONTENT;
//        mSmallWindowParams.height = WRAP_CONTENT;
//        mSmallWindowParams.gravity = Gravity.TOP | Gravity.START;
//        mSmallWindowParams.x = Resources.getSystem().getDisplayMetrics().widthPixels - mVideoViewWidth - dp12;
//        mSmallWindowParams.y = Resources.getSystem().getDisplayMetrics().heightPixels - mVideoViewHeight - dp12 - mStatusBarHeight - mNavigationBarHeight;
//
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
//            mSmallWindowParams.type = TYPE_TOAST;
//        } else {
//            mSmallWindowParams.type = TYPE_SYSTEM_ALERT;
//        }
//        mSmallWindowParams.flags = FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL | FLAG_HARDWARE_ACCELERATED | FLAG_LAYOUT_NO_LIMITS;
//        mSmallWindowParams.format = PixelFormat.RGBA_8888;
//        mSmallWindowParams.windowAnimations = android.R.style.Animation_Translucent;
//    }
}
