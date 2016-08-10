package com.drcosu.ndileber.tools;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.drcosu.ndileber.app.ActivityManager;
import com.drcosu.ndileber.app.SApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shidawei on 16/8/8.
 */
public class AndroidCrash implements Thread.UncaughtExceptionHandler{

    private String sdPath="sdcard/dileber_cache/"; //崩溃日志SD卡保存路径

    private static AndroidCrash instance = null;

    private AndroidCrash(){
        collectDeviceInfo();
    }

    /**
     *
     * @return
     */
    public static AndroidCrash getInstance() {
        if(instance==null){
            synchronized (AndroidCrash.class){
                if(instance==null){
                    instance = new AndroidCrash();
                }
            }
        }
        return instance;
    }

    public void setSdPath(String sdPath) {
        this.sdPath = sdPath;
    }


    /*
             * 进行重写捕捉异常
             *
             * @see
             * java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang
             * .Thread, java.lang.Throwable)
             */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(sdPath!=null){
            saveToSdcard(ex);
        }



        showToast( "很抱歉,程序发生异常,即将推出.");
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if(mDefaultCrashHandler!=null){
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();


    /**
     * 收集设备参数信息
     */
    public void collectDeviceInfo() {
        try {
            PackageManager pm = SApplication.getAppContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(SApplication.getAppContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e( "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logger.d( field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logger.e( "an error occured when collect crash info", e);
            }
        }
    }

    public String getCollectDeviceInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuilder.append(key + "=" + value + "\n");
        }
        return stringBuilder.toString();
    }

    private String getExceptionInfo(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        StringBuilder sb=new StringBuilder();
        sb.append("---------Crash Log Begin---------\n");
        sb.append(getCollectDeviceInfo()+"\n");
        sb.append(sw.toString()+"\n");
        sb.append("---------Crash Log End---------\n");
        return sb.toString();
    }

    private void saveToSdcard(Throwable ex) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file1 = new File(sdPath);
            if (!file1.exists()) {
                file1.mkdir();
            }
            File file2 = new File(file1.toString() + File.separator + UTime.getDateStr(UTime.Pattern.y_m_d_h_m_s,new Date()) + ".txt");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file2);
                fos.write(getExceptionInfo(ex).getBytes());
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                UUi.toast(ActivityManager.peekTopActivity(), msg, Toast.LENGTH_SHORT);

                Looper.loop();
            }
        }).start();
    }

}