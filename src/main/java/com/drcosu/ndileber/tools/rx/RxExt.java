package com.drcosu.ndileber.tools.rx;

import java.util.concurrent.TimeUnit;

import rx.Observable;


public class RxExt {

    private static final String TAG = "RxExt";

    public static <T> Observable.Transformer<T, T> clickThrottle() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.throttleFirst(500, TimeUnit.MILLISECONDS);
            }
        };
    }

}
