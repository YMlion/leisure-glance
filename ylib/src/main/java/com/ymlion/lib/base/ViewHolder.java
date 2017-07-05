package com.ymlion.lib.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * RecyclerView的ViewHolder
 *
 * Created by ymlion on 16/6/29.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    private View mConvertView;

    private ViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    /**
     * 获取ViewHolder实例
     *
     * @param context
     * @param parent
     * @param layoutId
     * @param viewType
     * @return
     */
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * 获取ViewHolder实例
     *
     * @param context
     * @param itemView
     * @return
     */
    public static ViewHolder get(Context context, View itemView) {
        return new ViewHolder(itemView);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId id
     * @return 控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId id
     * @param text 字符串
     * @return 对象本身
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId id
     * @param stringId string资源id
     * @return 对象本身
     */
    public ViewHolder setText(int viewId, int stringId) {
        TextView view = getView(viewId);
        view.setText(stringId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId id
     * @param drawableId 图片资源id
     * @return 对象本身
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId id
     * @param bm Bitmap对象
     * @return 实例本身
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

}
