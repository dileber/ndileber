package com.drcosu.ndileber.tools.rx;

import com.drcosu.ndileber.tools.log.ULog;
import rx.Observer;

/**
 * Created by congtaowang 2016/11/3.
 */

public abstract class RxNetworkResponseObserver<T> implements Observer<T> {

    public static final String TAG = RxNetworkResponseObserver.class.getSimpleName();

    public RxNetworkResponseObserver() {
    }


    @Override
    public final void onError(Throwable e) {
        ULog.e(e,TAG);
    }

    /**
     * Invoked when {@link Observer#onError(Throwable)}
     *
     * @param msg Detail error message to display.
     */
    public abstract void onResponseFail(String msg);

    @Override
    public final void onCompleted() {

    }

    @Override
    public final void onNext(T t) {
        try {
            onBeforeResponseOperation();
            onResponse(t);
        } catch (Exception e) {
            e.printStackTrace();

            onBeforeResponseOperation();
            onResponseStatusFail("11111111");
            ULog.e(TAG,"11111111");

            onResponseOptionFail();
            ULog.e(e,"Exception");
        } finally {
            onNextFinally();
        }
    }

    /**
     * Invoke before {@link RxNetworkResponseObserver#onResponse(Object)},
     * {@link RxNetworkResponseObserver#onResponseFail(String)},
     * {@link RxNetworkResponseObserver#onResponseStatusFail(String)}
     */
    public void onBeforeResponseOperation() {

    }

    /**
     * Invoked when {@link Observer#onNext(Object)} success.<br/>
     * If some exceptions maybe throws, implement {@link RxNetworkResponseObserver#onResponseOptionFail()}.
     *
     * @param t Response entity.
     */
    public abstract void onResponse(T t);

    /**
     * Invoked when response success but status false.
     *
     * @param msgCode From server.
     */
    public abstract void onResponseStatusFail(String msgCode);

    /**
     * Form some scene that need response entity.
     *
     * @param msgCode From server.
     * @param t       Response entity.
     */
    public void onResponseStatusFail(String msgCode, T t) {

    }

    /**
     * Invoked when {@link RxNetworkResponseObserver#onResponse(Object)} throws some exception.
     */
    public void onResponseOptionFail() {

    }

    /**
     * Invoked for some tail work.
     */
    public void onNextFinally() {

    }
}
