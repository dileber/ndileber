package com.drcosu.ndileber.tools.net;

/**
 * Created by shidawei on 2017/5/5.
 */

public class NetWorkException extends Exception{

    int code = 0;

    public NetWorkException(int code,String msg){
        super(msg);
        this.code = code;
    }

    public NetWorkException(String msg){
        super(msg);
    }

    public int getCode() {
        return code;
    }

}
