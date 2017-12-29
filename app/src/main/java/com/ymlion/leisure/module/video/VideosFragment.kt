package com.ymlion.leisure.module.video

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.YVideo
import com.ymlion.leisure.module.main.BaseCardFragment
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter

/**
 * A fragment representing a list of Items.
 */
class VideosFragment : BaseCardFragment<YVideo>() {

    override fun initAdapter() {
        datas = mutableListOf()
        mAdapter = VideoAdapter(datas, R.layout.item_video)
        (mAdapter as VideoAdapter).setOnItemClickListener { _, position ->
            val url = datas!![position].playLink
            val intent = Intent(context, VideoPlayActivity::class.java)
            intent.putExtra("url", url)
            context?.startActivity(intent)
        }
    }

    override fun getDatas(loadCache: Boolean) {
        var orderKey = 0L
        if (pageIndex > 1) {
            orderKey = datas?.last()?.orderKey!!
        }
        Http.build()
                .getVideos(20, orderKey)
                .subscribe(object : SubscriberAdapter<MutableList<YVideo>>() {
                    override fun onNext(t: MutableList<YVideo>?) {
                        super.onNext(t)
                        if (t != null) {
                            onLoadSuccess(t)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        onRefreshComplete()
                    }
                })
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(context, 2)

    companion object {
        fun newInstance(): VideosFragment {
            return VideosFragment()
        }
    }
}
