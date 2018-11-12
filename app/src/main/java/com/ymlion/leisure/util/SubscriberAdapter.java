package com.ymlion.leisure.util;

import android.util.Log;
import android.widget.Toast;

import com.ymlion.leisure.AppContext;

import io.reactivex.SingleObserver;
import io.reactivex.observers.DefaultObserver;

/**
 * subscriber适配器
 *
 * Created by ymlion on 16/6/30.
 */

public class SubscriberAdapter<T> extends DefaultObserver<T> implements SingleObserver<T> {

    private static final String TAG = "SubscriberAdapter";
    /** 是否显示提示 **/
    private boolean showTip;

    public SubscriberAdapter() {
    }

    public SubscriberAdapter(boolean showTip) {
        this.showTip = showTip;
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    @Override
    public void onSuccess(T t) {
        Log.d(TAG, "onSuccess");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError");
        e.printStackTrace();
        if (!showTip) {
            return;
        }
        Toast.makeText(AppContext.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext");
    }
}
