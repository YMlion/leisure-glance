package com.ymlion.leisure.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * base activity
 * <p>
 * Created by ymlion on 16/7/12.
 */

abstract public class BaseActivity extends RxAppCompatActivity {

    private GestureDetector mGestureDetector;
    private boolean mSwipeBackEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGestureDetector = new GestureDetector(this, new SwipeBackOnGestureListener());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isFinish = mGestureDetector.onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (isSwipeBackEnable() && isFinish) {
                finish();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可以滑动返回
     *
     * @return true：可以；false：不可以
     */
    public boolean isSwipeBackEnable() {
        return mSwipeBackEnable;
    }

    /**
     * 设置是否可以滑动返回
     *
     * @param swipeBackEnable true：可以；false：不可以
     */
    public void setSwipeBackEnable(boolean swipeBackEnable) {
        mSwipeBackEnable = swipeBackEnable;
    }

    private class SwipeBackOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float dx = e2.getX() - e1.getX();
            float dy = Math.abs(e2.getY() - e1.getY());
            float minDis = 100f;
            float minV = 500f;
            return ((dx > minDis && velocityX > 2000) || (dx > 300 && velocityX > minV)) && dy < minDis;
        }
    }
}
