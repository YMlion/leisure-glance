package com.ymlion.lib.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * rx util class
 *
 * @author ymlion
 * @date 2016/7/11
 */

public class RxUtil {

    /**
     * set rx thread
     *
     * @param <T>
     * @return
     */
    public static <T>Observable.Transformer<T, T> applyScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 异步任务
     *
     * @param action
     */
    public static void asyncDo(Action0 action) {
        Schedulers.io().createWorker().schedule(action);
    }
}
