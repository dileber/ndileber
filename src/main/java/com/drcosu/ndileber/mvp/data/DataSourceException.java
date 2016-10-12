package com.drcosu.ndileber.mvp.data;

/**
 * Created by shidawei on 16/8/4.
 */
public class DataSourceException extends Exception{
    private int code;
    public DataSourceException(String msg){
        super(msg);
    }

    public DataSourceException(String msg,Integer code){
        super(msg);
    }

    public int getCode() {
        return code;
    }
}
