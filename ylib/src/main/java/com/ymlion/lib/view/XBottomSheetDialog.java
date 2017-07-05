package com.ymlion.lib.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 设置默认高度及最大高度
 *
 * @author ymlion
 * @date 2016/11/2
 */

public class XBottomSheetDialog extends BottomSheetDialog {

    private int mPeekHeight;
    private int mMaxHeight;
    private boolean mCreated;
    private Window mWindow;
    private BottomSheetBehavior mBottomSheetBehavior;

    public XBottomSheetDialog(@NonNull Context context) {
        super(context);
        mWindow = getWindow();
    }

    public XBottomSheetDialog(@NonNull Context context, int peekHeight, int maxHeight) {
        this(context);
        mPeekHeight = peekHeight;
        mMaxHeight = maxHeight;
    }

    public XBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        mWindow = getWindow();
    }

    public XBottomSheetDialog(@NonNull Context context, boolean cancelable,
                                   OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mWindow = getWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreated = true;
        setPeekHeight();
        setMaxHeight();
    }

    public void setPeekHeight(int peekHeight) {
        mPeekHeight = peekHeight;
        if (mCreated) {
            setPeekHeight();
        }
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;
        if (mCreated) {
            setMaxHeight();
        }
    }

    private void setPeekHeight() {
        if (mPeekHeight <= 0) {
            return;
        }
        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setPeekHeight(mPeekHeight);
        }
    }

    private void setMaxHeight() {
        if (mMaxHeight <= 0) {
            return;
        }
        mWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, mMaxHeight);
        mWindow.setGravity(Gravity.BOTTOM);
    }

    private BottomSheetBehavior getBottomSheetBehavior() {
        if (mBottomSheetBehavior != null) {
            return mBottomSheetBehavior;
        }

        View view = mWindow.findViewById(android.support.design.R.id.design_bottom_sheet);
        if (view == null) {
            return null;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        return mBottomSheetBehavior;
    }
}
