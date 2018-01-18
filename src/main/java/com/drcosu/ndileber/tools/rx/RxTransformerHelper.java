package com.drcosu.ndileber.tools.rx;

import com.drcosu.ndileber.utils.schedulers.SchedulerProvider;
import rx.Observable;

public class RxTransformerHelper {

    public static <T> Observable.Transformer<T, T> ioToUI() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(SchedulerProvider.getInstance().io())
                        .unsubscribeOn(SchedulerProvider.getInstance().ui())
                        .observeOn(SchedulerProvider.getInstance().ui());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> newThreadToUI() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(SchedulerProvider.getInstance().newThread())
                        .unsubscribeOn(SchedulerProvider.getInstance().ui())
                        .observeOn(SchedulerProvider.getInstance().ui());
            }
        };
    }

}
