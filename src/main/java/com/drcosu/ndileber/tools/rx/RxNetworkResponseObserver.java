package com.drcosu.ndileber.tools.rx;

import com.drcosu.ndileber.mvp.data.model.SModel;
import com.drcosu.ndileber.tools.log.ULog;
import com.drcosu.ndileber.tools.net.NetWorkException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observer;

import static com.drcosu.ndileber.tools.net.RetCallback.networkMsg;
import static com.drcosu.ndileber.tools.net.RetCallback.networkTimeOutMsg;


public abstract class RxNetworkResponseObserver<T> implements Observer<T> {

    public static final String TAG = RxNetworkResponseObserver.class.getSimpleName();

    public RxNetworkResponseObserver() {
    }


    @Override
    public final void onError(Throwable e) {
        try {
            Throwable throwable = e;
            //获取最根源的异常
            while(throwable.getCause() != null){
                e = throwable;
                throwable = throwable.getCause();
            }
            onBeforeResponseOperation();
            if (e instanceof ConnectException) {

                ULog.d(networkMsg);
                onResponseFail(new NetWorkException(networkMsg));
            } else if(e instanceof SocketTimeoutException){
                ULog.d(networkTimeOutMsg);
                onResponseFail(new NetWorkException(networkTimeOutMsg));
            }else{
                ULog.e(e,"网络错误");
                onResponseFail(new NetWorkException("网络错误"));
            }
        } catch (Exception ex) {
            e.printStackTrace();
            ex.printStackTrace();
        }

    }

    /**
     * 错误
     * @param e
     */
    public abstract void onResponseFail(Exception e);

    @Override
    public final void onCompleted() {

    }

    @Override
    public final void onNext(T t) {
        onBeforeResponseOperation();
        if(t instanceof SModel){
            ULog.o(t);
        }
        onResponse(t);
    }

    /**
     * 执行一些起始操作
     */
    public abstract void onBeforeResponseOperation();

    /**
     * 返回值
     * @param t
     */
    public abstract void onResponse(T t);


}
