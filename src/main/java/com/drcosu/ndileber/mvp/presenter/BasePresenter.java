package com.drcosu.ndileber.mvp.presenter;

/**
 * Presenter基类
 * Created by shidawei on 16/6/2.
 */
public interface BasePresenter {

    /**
     * 第一次启动,准备数据，只执行一次
     */
    void start();

    /**
     * 销毁只执行一次
     */
    void onDestroy();

}
