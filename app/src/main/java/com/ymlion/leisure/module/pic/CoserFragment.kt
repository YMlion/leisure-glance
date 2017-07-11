package com.ymlion.leisure.module.pic

import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.Coser
import com.ymlion.leisure.module.main.BaseCardFragment
import com.ymlion.leisure.module.pic.adapter.CoserAdapter
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter
import rx.Observable
import java.util.*

/**
 * cosplay pictures.
 */
class CoserFragment : BaseCardFragment<Coser>() {

    override fun initAdapter() {
        datas = mutableListOf<Coser>()
        mAdapter = CoserAdapter(datas, R.layout.item_card_pic)
        (mAdapter as CoserAdapter).setOnItemClickListener { _, position ->
            Http.build()
                    .getCoserPhotos(datas!![position].id)
                    .flatMap { Observable.from(it) }
                    .map { it.url }
                    .toList()
                    .subscribe(object : SubscriberAdapter<List<String>>() {
                        override fun onNext(t: List<String>?) {
                            super.onNext(t)
                            if (t != null) {
                                GalleryActivity.start(context, t as ArrayList<String>?, position)
                            }
                        }
                    })
        }
    }

    override fun getDatas(loadCache: Boolean) {
        var lastId: Long = 0
        if (pageIndex > 1) {
            lastId = datas?.last()?.id!!
        }
        Http.build()
                .getCosers(PAGE_SIZE, lastId, loadCache)
                .subscribe(object : SubscriberAdapter<MutableList<Coser>>() {
                    override fun onNext(t: MutableList<Coser>?) {
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

    companion object {
        fun newInstance(): CoserFragment {
            return CoserFragment()
        }
    }
}
