package com.drcosu.ndileber.tools;

import android.os.Looper;
import android.widget.Toast;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.tools.crash.CrashSaver;

/**
 * Created by shidawei on 16/8/8.
 */
public class AndroidCrash{

    private static AndroidCrash instance = null;

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private AndroidCrash(){
        // get default
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();

        // install
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {
                // save log
                saveException(ex, true);
                showToast( "很抱歉,程序发生异常,即将推出.");
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // uncaught
                mDefaultCrashHandler.uncaughtException(thread, ex);
            }
        });
    }

    public static AndroidCrash getInstance() {
        if (instance == null) {
            instance = new AndroidCrash();
        }

        return instance;
    }

    public final void saveException(Throwable ex, boolean uncaught) {
        CrashSaver.save(SApplication.getAppContext(), ex, uncaught);
    }

    public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        if (handler != null) {
            this.mDefaultCrashHandler = handler;
        }
    }

    /**
     * 进行弹出框提示
     *
     * @param msg
     */
    private void showToast( final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                UUi.toast(ActivityManager.getCurrentActivity(), msg, Toast.LENGTH_SHORT);
                Looper.loop();
            }
        }).start();
    }

}