package com.ymlion.lib.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView适配器
 *
 * Created by ymlion on 16/6/28.
 */

public abstract class RvBaseAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mDatas;
    protected int mLayoutRes;
    private OnItemClickListener mListener;
    private List<View> mHeaders;
    private List<View> mFooters;

    public RvBaseAdapter(List<T> list, int layoutRes) {
        mDatas = list;
        mLayoutRes = layoutRes;
    }

    @Override
    public int getItemViewType(int position) {
        int headNum = getHeadNum();

        if (position < headNum) {
            return ++position;
        } else if (position >= headNum + mDatas.size()) {
            return position - mDatas.size() + 1;
        }

        return super.getItemViewType(position);
    }

    private int getHeadNum() {
        int headNum = 0;
        if (mHeaders != null) {
            headNum = mHeaders.size();
        }
        return headNum;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return ViewHolder.get(parent, mLayoutRes, viewType);
        }
        int headNum = getHeadNum();
        if (viewType <= headNum) {
            return ViewHolder.get(mHeaders.get(--viewType));
        } else {
            return ViewHolder.get(mFooters.get(--viewType - headNum));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mListener != null) {
            ((ViewHolder) holder).getConvertView().setOnClickListener(view -> mListener.onItemClick(view, position));
        }
        int h = getHeadNum();
        if (position >= h && position < h + mDatas.size()) {
            onBind(viewHolder, mDatas.get(position - h));
        }
    }

    public abstract void onBind(ViewHolder holder, T model);

    @Override
    public int getItemCount() {
        int h = mHeaders == null ? 0 : mHeaders.size();
        int f = mFooters == null ? 0 : mFooters.size();
        int d = mDatas == null ? 0 : mDatas.size();
        return h + f + d;
    }

    public T getItem(int position) {
        int h = getHeadNum();
        if (position >= h && position < h + mDatas.size()) {
            return mDatas.get(position - h);
        } else {
            return null;
        }
    }

    /**
     * data list
     *
     * @return the dataList
     */

    protected List<T> getDataList() {
        return mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setmDatas(List<T> dataList) {
        if (dataList == null) {
            return;
        }
        this.mDatas = dataList;
        notifyDataSetChanged();
    }

    /**
     * @param dataList
     *            the data to set
     */
    public void setDatas(T[] dataList) {
        if (dataList == null) {
            return;
        }
        mDatas = new ArrayList<>(Arrays.asList(dataList));
        notifyDataSetChanged();
    }

    /**
     * 添加header
     *
     * @param header header view
     */
    public void addHeader(View header) {
        if (header == null) {
            return;
        }
        if (mHeaders == null) {
            mHeaders = new ArrayList<>();
        }
        mHeaders.add(header);
    }

    /**
     * 添加footer
     *
     * @param footer footer view
     */
    public void addFooter(View footer) {
        if (footer == null) {
            return;
        }
        if (mFooters == null) {
            mFooters = new ArrayList<>();
        }
        mFooters.add(footer);
    }

    /**
     *Interface definition for a callback to be invoked when an item in this RecyclerView has been clicked.
     */
    public interface OnItemClickListener {
        /**
         *Callback method to be invoked when an item in this RecyclerView has been clicked.
         *
         * @param view The view within the RecyclerView that was clicked (this will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(View view, int position);
    }
}
