package com.drcosu.ndileber.mvp.presenter;

import rx.Subscription;

/**
 * Created by WaTaNaBe on 2017/9/30.
 */

public interface IRxPresenter {

    void unsubscribe();

    void subscribe();

    void add(Subscription s);

    void remove(Subscription s);

    void addAll(Subscription... subscriptions);
}
