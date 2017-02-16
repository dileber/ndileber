package com.drcosu.ndileber.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by H2 on 2016/9/28.
 */
public class TVersion {

    private static int versionCode = 0;

    private static String versionName = null;

    //版本名
    public static String getVersionName(Context context) {
        if(versionName==null){
            return getPackageInfo(context).versionName;
        }
        return versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        if(versionCode==0){
            return getPackageInfo(context).versionCode;
        }else{
            return versionCode;
        }
    }
    //获取版本信息
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            versionCode = pi.versionCode;
            versionName = pi.versionName;
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

}
