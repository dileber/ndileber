package com.drcosu.ndileber.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.drcosu.ndileber.app.SApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统类
 * Created by shidawei on 16/2/24.
 */
public class SSystem {


    /**
     * ro.serialno的用处是来保存唯一设备号
     * @return
     */
    public static String getOnlyId(){
        return getAndroidOsSystemProperties("ro.serialno");
    }
    /**
     *
     * @param key
     * @return
     */
    private static String getAndroidOsSystemProperties(String key) {
        String ret;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            ret = (String) get.invoke(c, key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    /**
     * 判断是否有sd卡
     * @return
     */
    public static boolean isSdcard(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否有这个软件包
     * @param packageName
     * @return
     */
    public static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 通过进程id获取包名
     * @param pID
     * @return
     */
    public static String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) SApplication.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = SApplication.getAppContext().getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 判断当前进程是否是主进程
     * @param context
     * @return
     */
    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取当前屏幕的信息
     * @return
     */
    public static Screen getScreen() {
        Screen screen = new Screen();
        DisplayMetrics dm = SApplication.getAppContext().getResources().getDisplayMetrics();
        screen.screenWidth = dm.widthPixels;
        screen.screenHeight = dm.heightPixels;
        screen.screenMin = (screen.screenWidth > screen.screenHeight) ? screen.screenHeight : screen.screenWidth;
        screen.screenMax = (screen.screenWidth < screen.screenHeight) ? screen.screenHeight : screen.screenWidth;
        screen.density = dm.density;
        screen.scaleDensity = dm.scaledDensity;
        screen.xdpi = dm.xdpi;
        screen.ydpi = dm.ydpi;
        screen.densityDpi = dm.densityDpi;
        return screen;
    }

    public static class Screen {
        int screenWidth;
        int screenHeight;

        int screenMax;
        int screenMin;

        float density;
        float scaleDensity;
        float xdpi;
        float ydpi;
        int densityDpi;

        public int getScreenWidth() {
            return screenWidth;
        }

        public void setScreenWidth(int screenWidth) {
            this.screenWidth = screenWidth;
        }

        public int getScreenHeight() {
            return screenHeight;
        }

        public void setScreenHeight(int screenHeight) {
            this.screenHeight = screenHeight;
        }

        public float getDensity() {
            return density;
        }

        public void setDensity(float density) {
            this.density = density;
        }

        public float getScaleDensity() {
            return scaleDensity;
        }

        public void setScaleDensity(float scaleDensity) {
            this.scaleDensity = scaleDensity;
        }

        public float getXdpi() {
            return xdpi;
        }

        public void setXdpi(float xdpi) {
            this.xdpi = xdpi;
        }

        public float getYdpi() {
            return ydpi;
        }

        public void setYdpi(float ydpi) {
            this.ydpi = ydpi;
        }

        public int getDensityDpi() {
            return densityDpi;
        }

        public void setDensityDpi(int densityDpi) {
            this.densityDpi = densityDpi;
        }

        public int getScreenMax() {
            return screenMax;
        }

        public void setScreenMax(int screenMax) {
            this.screenMax = screenMax;
        }

        public int getScreenMin() {
            return screenMin;
        }

        public void setScreenMin(int screenMin) {
            this.screenMin = screenMin;
        }
    }


}
