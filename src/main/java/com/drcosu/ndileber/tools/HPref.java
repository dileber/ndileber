package com.drcosu.ndileber.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.drcosu.ndileber.app.FrameContants;
import com.drcosu.ndileber.app.SApplication;
import com.drcosu.ndileber.mvp.data.model.SModel;
import com.drcosu.ndileber.mvp.data.model.SWrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by shidawei on 16/7/8.
 */
public class HPref {

    private static volatile HPref instance;

    public static HPref getInstance() {
        if (instance == null) {
            synchronized (HPref.class) {
                instance = new HPref();
            }
        }
        return instance;
    }

    private Map<String, SharedPreferences> prefs;

    private HPref() {
        prefs = new HashMap<String,SharedPreferences>();
    }

    public SharedPreferences getPreferance(String name) {
        if (name == null) {
            name = SApplication.getInstance().getPackageName() + "_preferences";
        }
        if (!prefs.containsKey(name)) {
            SharedPreferences pref = SApplication.getInstance()
                    .getSharedPreferences(name, Context.MODE_PRIVATE);
            prefs.put(name, pref);
        }
        return prefs.get(name);
    }


    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public synchronized boolean put(String name,String key, Object object)
    {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(key,object);
        return putSome(name,map);
    }




    public synchronized boolean  putSome(String name,Map<String,Object> map) {
        SharedPreferences sp = getPreferance(name);
        SharedPreferences.Editor editor = sp.edit();
        for (String key : map.keySet()) {
            Object ret = map.get(key);

            if (ret == null) {
                editor.putString(key, null);
            } else {
                Class<?> returnType = ret.getClass();
                if (returnType.isAssignableFrom(int.class)
                        || returnType.isAssignableFrom(Integer.class)) {
                    editor.putInt(key, ret == null ? 0 : Integer.parseInt(ret.toString()));
                } else if (returnType.isAssignableFrom(boolean.class)
                        || returnType.isAssignableFrom(Boolean.class)) {
                    editor.putBoolean(key,
                            ret == null ? false : Boolean.parseBoolean(ret.toString()));
                } else if (returnType.isAssignableFrom(long.class)
                        || returnType.isAssignableFrom(Long.class)) {
                    editor.putLong(key, ret == null ? 0L : Long.parseLong(ret.toString()));
                } else if (returnType.isAssignableFrom(double.class)
                        || returnType.isAssignableFrom(Double.class)) {
                    editor.putLong(
                            key,
                            ret == null ? Double.doubleToRawLongBits(0.0) : Double
                                    .doubleToRawLongBits(Double.parseDouble(ret
                                            .toString())));
                } else if (returnType.isAssignableFrom(float.class)
                        || returnType.isAssignableFrom(Float.class)) {
                    editor.putFloat(key,
                            ret == null ? 0f : Float.parseFloat(ret.toString()));
                } else if (returnType.isAssignableFrom(byte.class)
                        || returnType.isAssignableFrom(Byte.class)) {
                    editor.putInt(key, ret == null ? 0 : Byte.parseByte(ret.toString()));
                } else if (returnType.isAssignableFrom(short.class)
                        || returnType.isAssignableFrom(Short.class)) {
                    editor.putInt(key, ret == null ? 0 : Short.parseShort(ret.toString()));
                } else if (returnType.isAssignableFrom(Set.class)) {
                    editor.putStringSet(key,ret == null ? new HashSet<String>() :(Set<String>)ret);
                } else if(SModel.class.isAssignableFrom(returnType)
                        || SWrapper.class.isAssignableFrom(returnType)){
                    editor.putString(key,HJson.toJson(ret));
                }else {
                    editor.putString(key, ret.toString());
                }
            }

        }

        return editor.commit();


    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @return
     */
    public synchronized <T> T get(String name, String k, T defaultValue,Class<T> returnType) {

        SharedPreferences sp = this.getPreferance(name);
        if (returnType.isAssignableFrom(int.class)
                || returnType.isAssignableFrom(Integer.class)) {
            return (T) Integer.valueOf(sp.getInt(k, (Integer) defaultValue));
        } else if (returnType.isAssignableFrom(boolean.class)
                || returnType.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf(sp.getBoolean(k,
                    (Boolean) defaultValue));
        } else if (returnType.isAssignableFrom(long.class)
                || returnType.isAssignableFrom(Long.class)) {
            return (T) Long.valueOf(sp.getLong(k, (Long) defaultValue));
        } else if (returnType.isAssignableFrom(double.class)
                || returnType.isAssignableFrom(Double.class)) {
            return (T) Double.valueOf(Double.longBitsToDouble(sp.getLong(k,
                    (Long) defaultValue)));
        } else if (returnType.isAssignableFrom(float.class)
                || returnType.isAssignableFrom(Float.class)) {
            return (T) Float.valueOf(sp.getFloat(k, (Float) defaultValue));
        } else if (returnType.isAssignableFrom(byte.class)
                || returnType.isAssignableFrom(Byte.class)) {
            return (T) Byte.valueOf((byte) sp.getInt(k,
                    (Integer) defaultValue));
        } else if (returnType.isAssignableFrom(short.class)
                || returnType.isAssignableFrom(Short.class)) {
            return (T) Short.valueOf((short) sp.getInt(k,
                    (Integer) defaultValue));
        }else if(returnType.isAssignableFrom(Set.class)){
            return (T)  sp.getStringSet(k, (Set<String>) defaultValue);
        } else if(SModel.class.isAssignableFrom(returnType)
                || SWrapper.class.isAssignableFrom(returnType)){
            String value = sp.getString(k,(String)defaultValue);
            if(value==null){
                return null;
            }else{
                return HJson.fromJson(value,returnType);
            }
        }{
            return (T) sp.getString(k, defaultValue == null ? null
                    : defaultValue.toString());
        }
    }

    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public synchronized boolean remove(String name, String key)
    {
        SharedPreferences sp = getPreferance(name);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除数据
     */
    public synchronized boolean clear(String name)
    {
        SharedPreferences sp = getPreferance(name);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }



    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public synchronized boolean contains(String name, String key)
    {
        SharedPreferences sp = getPreferance(name);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public synchronized Map<String, ?> getAll(String name)
    {
        SharedPreferences sp = getPreferance(name);
        return sp.getAll();
    }



}
