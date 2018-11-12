package com.ymlion.lib.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public static <T> ObservableTransformer<T, T> applyScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 异步任务
     *
     * @param action
     */
    public static void asyncDo(Runnable action) {
        Schedulers.io().createWorker().schedule(action);
    }
}
