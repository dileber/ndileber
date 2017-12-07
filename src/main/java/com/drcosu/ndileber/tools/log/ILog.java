package com.drcosu.ndileber.tools.log;

import java.util.Objects;

/**
 * Created by WaTaNaBe on 2017/8/19.
 */

public interface ILog {

    void init(String tag,boolean debug);

    //void d(Object object);

    void d(Object... message);

    void e(Object... message);

    void e(Throwable throwable, Object... message);

    void w(Object... message);

    void i(Object... message);

    void v(Object... message);

    void wtf(Object... message);

    void dm(String message, Object... args);

    void em(String message, Object... args);

    void em(Throwable throwable, String message, Object... args);

    void wm(String message, Object... args);

    void im(String message, Object... args);

    void vm(String message, Object... args);

    void wtfm(String message, Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(String xml);

    /**
     * 打印对象
     * @param o
     */
    void o(Object o);

    void dt(String tag,Object... message);

    void et(String tag,Object... message);

    void et(String tag,Throwable throwable, Object... message);

    void wt(String tag,Object... message);

    void it(String tag,Object... message);

    void vt(String tag,Object... message);

    void wtft(String tag,Object... message);

    void dmt(String tag,String message, Object... args);

    void emt(String tag,String message, Object... args);

    void emt(String tag,Throwable throwable, String message, Object... args);

    void wmt(String tag,String message, Object... args);

    void imt(String tag,String message, Object... args);

    void vmt(String tag,String message, Object... args);

    void wtfmt(String tag,String message, Object... args);

    /**
     * Formats the given json content and print it
     */
    void jsont(String tag,String json);

    /**
     * Formats the given xml content and print it
     */
    void xmlt(String tag,String xml);

    /**
     * 打印对象
     * @param o
     */
    void ot(String tag,Object o);

}
