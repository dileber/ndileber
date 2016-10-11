package com.drcosu.ndileber.mvp.data.model;

/**
 * Created by shidawei on 16/3/12.
 * 包装类
 */
public class SWrapper{

    public SWrapper() {
    }

    public SWrapper(Integer state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    Integer state;
    String msg;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
