package com.drcosu.ndileber.tools.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;

import com.drcosu.ndileber.tools.HJson;
import com.drcosu.ndileber.tools.HString;
import com.drcosu.ndileber.tools.storage.StorageType;
import com.drcosu.ndileber.tools.storage.UStorage;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;

/**
 * Created by WaTaNaBe on 2017/8/19.
 */

public class OrhanobutLogImpl implements ILog {

    public void init(String tag, final boolean debug){
        Logger.clearLogAdapters();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)         // 决定打印多少行（每一行代表一个方法）默认：2
                .methodOffset(3)        // 设置方法的偏移量
                .tag(tag)   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
    }

    @Override
    public void d(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.d(s);
    }

    /**
     * 打印数据等数据
     * @param message
     */
    @Override
    public void d(Object message) {
        Logger.d(message);
    }

    @Override
    public void e(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.e(s);
    }

    @Override
    public void e(Throwable throwable, Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.e(throwable,s);
    }

    @Override
    public void w(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.w(s);
    }

    @Override
    public void i(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.i(s);
    }

    @Override
    public void v(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.v(s);
    }

    @Override
    public void wtf(Object... message) {
        String s = HString.concatObject(" ",message);
        Logger.wtf(s);
    }

    /**
     * Logger.d("hello %s %d", "world", 100);
     * @param message
     * @param args
     */
    @Override
    public void dm(String message, Object... args) {
        Logger.d(message,args);
    }

    @Override
    public void em(String message, Object... args) {
        Logger.e(message,args);
    }

    @Override
    public void em(Throwable throwable, String message, Object... args) {
        Logger.e(throwable,message,args);
    }

    @Override
    public void wm(String message, Object... args) {
        Logger.w(message,args);
    }

    @Override
    public void im(String message, Object... args) {
        Logger.i(message,args);
    }

    @Override
    public void vm(String message, Object... args) {
        Logger.v(message,args);
    }

    @Override
    public void wtfm(String message, Object... args) {
        Logger.wtf(message,args);
    }

    @Override
    public void json(String json) {
        Logger.json(json);
    }

    @Override
    public void xml(String xml) {
        Logger.xml(xml);
    }

    @Override
    public void o(Object o) {
        json(HJson.toJson(o));
    }
}
