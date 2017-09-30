package com.drcosu.ndileber.mvp.presenter;

import android.support.annotation.NonNull;

import com.drcosu.ndileber.mvp.view.BaseView;
import com.drcosu.ndileber.utils.schedulers.BaseSchedulerProvider;
import com.drcosu.ndileber.utils.schedulers.SchedulerProvider;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by WaTaNaBe on 2017/9/30.
 */

public abstract class NormalRxPresenter<T1 extends BaseView> implements BasePresenter,IRxPresenter {
    protected T1 mView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    protected CompositeSubscription mSubscriptions;

    public NormalRxPresenter(@NonNull T1 view){
        this.mView = view;
        mSchedulerProvider = SchedulerProvider.getInstance();
        mSubscriptions = new CompositeSubscription();
        this.mView.setPresenter(this);
    }

    @Override
    public void onResume() {
        subscribe();
    }

    @Override
    public void onPause() {
        unsubscribe();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void remove(Subscription s) {
        mSubscriptions.remove(s);
    }

    @Override
    public void add(Subscription s) {
        mSubscriptions.add(s);
    }

    @Override
    public void addAll(Subscription... subscriptions) {
        mSubscriptions.addAll(subscriptions);
    }
}
