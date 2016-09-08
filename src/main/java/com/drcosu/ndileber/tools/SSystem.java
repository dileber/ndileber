package com.drcosu.ndileber.tools;

import android.os.Environment;

import java.io.File;
import java.lang.reflect.Method;

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
}
