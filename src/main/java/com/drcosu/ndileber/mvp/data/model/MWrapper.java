package com.drcosu.ndileber.mvp.data.model;

/**
 * Created by shidawei on 16/8/8.
 */
public class MWrapper<T> extends SWrapper{

    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
