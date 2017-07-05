package com.ymlion.lib.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @author YML
 * @date 2016/10/25
 */

public class DiffCallback<T> extends DiffUtil.Callback {

    private List<T> mNewData;
    private List<T> mOldData;

    public DiffCallback(List<T> newData, List<T> oldData) {
        mNewData = newData;
        mOldData = oldData;
    }
    @Override
    public int getOldListSize() {
        return mOldData == null ? 0 : mOldData.size();
    }

    @Override
    public int getNewListSize() {
        return mNewData == null ? 0 : mNewData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).equals(mNewData.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition).equals(mNewData.get(newItemPosition));
    }
}
