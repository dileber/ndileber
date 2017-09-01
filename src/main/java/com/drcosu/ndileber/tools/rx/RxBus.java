package com.drcosu.ndileber.tools.rx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 *
 * ticky事件只指事件消费者在事件发布之后才注册的也能接收到该事件的特殊类型。
 * Android中就有这样的实例，也就是Sticky Broadcast，即粘性广播。
 * 正常情况下如果发送者发送了某个广播，而接收者在这个广播发送后才注册自己的Receiver，这时接收者便无法接收到刚才的广播，
 * 为此Android引入了StickyBroadcast，在广播发送结束后会保存刚刚发送的广播（Intent），
 * 这样当接收者注册完Receiver后就可以接收到刚才已经发布的广播。这就使得我们可以预先处理一些事件，让有消费者时再把这些事件投递给消费者。
 * Created by WaTaNaBe on 2017/9/1.
 */

public class RxBus {

    /**
     * PublishSubject，BehaviorSubject ，BehaviorSubject，AsyncSubject。
     * PublishSubject只会给在订阅者订阅的时间点之后的数据发送给观察者。
     * BehaviorSubject在订阅者订阅时，会发送其最近发送的数据（如果此时还没有收到任何数据，它会发送一个默认值）。
     * ReplaySubject在订阅者订阅时，会发送所有的数据给订阅者，无论它们是何时订阅的。
     * AsyncSubject只在原Observable事件序列完成后，发送最后一个数据，后续如果还有订阅者继续订阅该Subject, 则可以直接接收到最后一个值。
     */

    private Subject<Object, Object> bus = new SerializedSubject(PublishSubject.create());
    private Map<Class<?>, Object> stickyEvents = new ConcurrentHashMap<>();

    private RxBus() {

    }

    private void rebuild() {
        bus = new SerializedSubject(PublishSubject.create());
        stickyEvents.clear();
    }

    private void postStickyEventImpl(Object event) {
        synchronized (stickyEvents) {
            stickyEvents.put(event.getClass(), event);
        }
        post(event);
    }

    private <T> Observable<T> toObservableStickyImpl(final Class<T> klass) {
        synchronized (stickyEvents) {
            final Object event = stickyEvents.get(klass);
            if (event != null) {
                return bus.ofType(klass).mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(klass.cast(event));
                    }
                }));
            }
            return bus.ofType(klass);
        }
    }

    private static final class Holder {
        private static final RxBus BUS = new RxBus();
    }

    private static RxBus inst() {
        return Holder.BUS;
    }

    /**
     * 发送事件
     * @param event
     */
    public static void post(Object event) {
        inst().bus.onNext(event);
    }

    /**
     * 消费事件
     * @param klass
     * @param <T>
     * @return
     */
    public static <T> Observable<T> toObservable(Class<T> klass) {
        return inst().bus.ofType(klass);
    }

    /**
     * 判断 Subject 是否已经有 observers 订阅了 有则返回 ture
     * @return
     */
    public static boolean hasObservers() {
        return inst().bus.hasObservers();
    }

    public static void reset() {
        synchronized (inst()) {
            inst().rebuild();
        }
    }

    /**
     * 发送特殊的Sticky 事件
     * @param event
     */
    public static void postSticky(Object event) {
        inst().postStickyEventImpl(event);
    }

    /**
     * 接受特殊的Sticky事件
     * @param klass
     * @param <T>
     * @return
     */
    public static <T> Observable<T> toObservableSticky(Class<T> klass) {
        return inst().toObservableStickyImpl(klass);
    }

//    /**
//     * 根据eventType获取Sticky事件
//     */
//    public <T> T getStickyEvent(Class<T> eventType) {
//        synchronized (stickyEvents) {
//            return eventType.cast(stickyEvents.get(eventType));
//        }
//    }
//
//    /**
//     * 移除指定eventType的Sticky事件
//     */
//    public <T> T removeStickyEvent(Class<T> eventType) {
//        synchronized (stickyEvents) {
//            return eventType.cast(stickyEvents.remove(eventType));
//        }
//    }


    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEventsImpl() {
        synchronized (stickyEvents) {
            stickyEvents.clear();
        }
    }

    public static void removeAllStickyEvents() {
        inst().removeAllStickyEventsImpl();
    }
}

