package com.ymlion.leisure.module.video

import com.bumptech.glide.Glide
import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.YVideo
import com.ymlion.lib.base.RvBaseAdapter
import com.ymlion.lib.base.ViewHolder

class VideoAdapter(list: MutableList<YVideo>?, layoutRes: Int) : RvBaseAdapter<YVideo>(list, layoutRes) {
    override fun onBind(holder: ViewHolder, model: YVideo) {
        holder.setText(R.id.tv_video_type, model.clientTags)
        holder.setText(R.id.tv_video_time, model.duration)
        holder.setText(R.id.tv_video_title, model.title)
        Glide.with(holder.convertView.context)
                .load(model.img)
                .into(holder.getView(R.id.iv_video))
    }

}
