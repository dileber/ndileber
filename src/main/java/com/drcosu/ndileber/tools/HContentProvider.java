package com.drcosu.ndileber.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.tools.log.ULog;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jing on 2016/8/24.
 */
public class HContentProvider {

    ContentResolver mContentResolver;

    public static volatile HContentProvider instance ;

    private HContentProvider(Context context){
        mContentResolver = SApplication.getAppContext().getContentResolver();
    }

    public static HContentProvider getInstance() {
        if (instance == null) {
            synchronized (HContentProvider.class) {
                instance = new HContentProvider(SApplication.getAppContext());
            }
        }
        return instance;
    }

    public ContentResolver getmContentResolver() {
        return mContentResolver;
    }

    public <T>List<T> query(String[] projection, String orderBy,Uri uri, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> mList = new ArrayList<T>();
        Cursor cursor = mContentResolver.query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return mList;
        }
        while (cursor.moveToNext()) {
            T t = clazz.newInstance();
            for(int i=0;i<projection.length;i++){
                //ULog.d(projection[i]+":"+cursor.getString(i));
                Field field = clazz.getDeclaredField(projection[i]);
                field.setAccessible(true);
                invokeSet(t,projection[i], cursor.getString(cursor.getColumnIndex(projection[i])),field.getType());
            }
            mList.add(t);
        }
        return mList;
    }

    public static <T>void  invokeSet(T t, String fieldName, String value,Class<? extends Object> typeClass) {
        if(value==null){
            return;
        }
        Method method = getSetMethod(t.getClass(), fieldName);
        try {
            Constructor<? extends Object> cons = typeClass.getConstructor(String.class);
            Object attribute = cons.newInstance(value);
            method.invoke(t, new Object[] { attribute });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java反射bean的set方法
     * @param objectClass
     * @param fieldName
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Method getSetMethod(Class objectClass, String fieldName) {
        try {
            Class[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            Method method = objectClass.getMethod(sb.toString(), parameterTypes);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
