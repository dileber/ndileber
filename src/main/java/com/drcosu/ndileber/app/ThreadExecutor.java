package com.drcosu.ndileber.app;

import com.drcosu.ndileber.tools.log.ULog;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * Created by shidawei on 16/7/13.
 */
public class ThreadExecutor {

    ScheduledExecutorService executor;

    private volatile static ThreadExecutor instance = null;

    public static ThreadExecutor getInstance(){
        if(instance==null){
            synchronized (ThreadExecutor.class){
                if(instance==null){
                    return new ThreadExecutor();
                }
            }
        }
        return instance;
    }


    public ThreadExecutor(){
        executor = Executors.newScheduledThreadPool(10);
    }

    public void stop() {
        ULog.w("关闭线程池");
        this.executor.shutdown();
    }

    public void startOnce(Runnable runnable, long delay) {
        this.executor.schedule(runnable, delay, TimeUnit.SECONDS);
    }

    public Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

}
