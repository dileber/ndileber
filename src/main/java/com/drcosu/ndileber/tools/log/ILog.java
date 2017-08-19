package com.drcosu.ndileber.tools.log;

import java.util.Objects;

/**
 * Created by WaTaNaBe on 2017/8/19.
 */

public interface ILog {

    void init(String tag,boolean debug);

    //void d(Object object);

    void d(Object... message);

    void d(Object message);

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

}
