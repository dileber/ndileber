package com.drcosu.ndileber.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.drcosu.ndileber.app.SApplication;

import java.io.File;
import java.lang.reflect.Method;
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
    private String getAppName(int pID) {
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

}
