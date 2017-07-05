package com.ymlion.leisure.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ymlion.leisure.R;
import com.ymlion.leisure.ui.model.Meizi;
import com.ymlion.lib.base.RvBaseAdapter;
import com.ymlion.lib.base.ViewHolder;

import java.util.List;

/**
 * 妹子适配器
 *
 * @author ymlion
 * @date 2016/7/9
 */
public class FuliAdapter extends RvBaseAdapter<Meizi> {

    public FuliAdapter(Context context, List<Meizi> list, int layoutRes) {
        super(context, list, layoutRes);
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBind(final ViewHolder holder, Meizi model) {
        final ImageView img = holder.getView(R.id.fuli_iv);
        img.setTransitionName(model.get_id());
        holder.setText(R.id.fuli_des_tv, model.getDesc());
        Glide.with(mContext)
                .load(model.getUrl())
                .apply(new RequestOptions().dontAnimate())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Log.e(TAG, "onResourceReady: " + resource.getWidth() + " " + resource.getHeight());
                        View parent = (View) img.getParent();
                        final int width = parent.getWidth();
                        final int height = (int) (width / 1.0f / resource.getIntrinsicWidth() * resource.getIntrinsicHeight());
                        // Log.e(TAG, "onResourceReady: view :  " + width + " " + height);
                        ViewGroup.LayoutParams lp = img.getLayoutParams();
                        lp.width = width;
                        lp.height = height;
                        img.setLayoutParams(lp);
                        return false;
                    }
                })
                .into(img);
    }
}
