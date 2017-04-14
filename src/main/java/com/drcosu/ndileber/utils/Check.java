package com.drcosu.ndileber.utils;

import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by shidawei on 16/6/2.
 * 检查工具
 */
public class Check {

    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static String checkStringNotEmpty(String str, @Nullable Object errorMessage){
        if (str == null||str.trim().equals("")) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return str.trim();
    }

    public static boolean checkNotNulls(Object ... objects){
        if(objects==null){
            return false;
        }
        for(int i=0;i<objects.length;i++){
            if(objects[i]==null){
                return false;
            }
        }
        return true;
    }


}
