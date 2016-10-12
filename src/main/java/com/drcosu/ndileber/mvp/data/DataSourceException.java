package com.drcosu.ndileber.mvp.data;

/**
 * Created by shidawei on 16/8/4.
 */
public class DataSourceException extends Exception{
    private Integer code;
    public DataSourceException(String msg){
        super(msg);
    }

    public DataSourceException(String msg,Integer code){
        super(msg);
    }

    public Integer getCode() {
        return code;
    }
}
