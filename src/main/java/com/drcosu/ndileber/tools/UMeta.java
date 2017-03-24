package com.drcosu.ndileber.tools;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * Created by shidawei on 2017/3/24.
 */
public final class UMeta {

    /**
     * 获取Activity 的meta
     * @param activity
     * @param metaName
     * @return
     */
    public static String getActivityMeta(Activity activity, String metaName){
        ActivityInfo info= null;
        try {
            info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(),
                            PackageManager.GET_META_DATA);
            return info.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *获取Application 的meta
     * @param context
     * @param metaName
     * @return
     */
    public static String getApplicationMeta(Context context, String metaName){
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *获取Service 的meta
     * @param context
     * @param clazz
     * @param metaName
     * @return
     */
    public static String getServiceMeta(Context context, Class<Service> clazz, String metaName){
        ComponentName cn=new ComponentName(context, clazz);
        ServiceInfo info = null;
        try {
            context.getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Receiver 的meta
     * @param context
     * @param clazz
     * @param metaName
     * @return
     */
    public static String getReceiverMeta(Context context, Class<BroadcastReceiver> clazz, String metaName){
        ComponentName cn=new ComponentName(context, clazz);
        ActivityInfo info= null;
        try {
            info = context.getPackageManager()
                    .getReceiverInfo(cn,
                            PackageManager.GET_META_DATA);
            return info.metaData.getString(metaName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
