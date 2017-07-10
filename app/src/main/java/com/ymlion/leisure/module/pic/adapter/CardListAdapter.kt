package com.ymlion.leisure.module.pic.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ymlion.leisure.R
import com.ymlion.lib.base.RvBaseAdapter
import com.ymlion.lib.base.ViewHolder

/**
 * Created by YMlion on 2017/7/7.
 */
abstract class CardListAdapter<T>(list: MutableList<T>?, layoutRes: Int) : RvBaseAdapter<T>(list, layoutRes) {

    override fun onBind(holder: ViewHolder, model: T) {
        val img = holder.getView<ImageView>(R.id.iv_card)
        holder.setText(R.id.tv_card_title, getTitle(model))
        Glide.with(holder.convertView.context)
                .load(getUrl(model))
                .apply(RequestOptions().dontAnimate())
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        var parent: View = img.parent as View
                        val width = parent.width
                        val height = width / 1.0f / resource!!.intrinsicWidth * resource.intrinsicHeight
                        var lp = img.layoutParams
                        lp.width = width
                        lp.height = height.toInt()
                        Log.d("CardListAdapter", "width : $width; height : $height")
                        img.layoutParams = lp

                        return false
                    }
                })
                .into(img)
    }

    abstract fun getUrl(model: T): String

    abstract fun getTitle(model: T): String
}