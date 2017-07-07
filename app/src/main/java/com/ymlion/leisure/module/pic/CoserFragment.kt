package com.ymlion.leisure.module.pic

import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.Coser
import com.ymlion.leisure.module.pic.adapter.CoserAdapter
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter

/**
 * cosplay pictures.
 */
class CoserFragment : BasePicFragment<Coser>() {

    override fun initAdapter() {
        datas = mutableListOf<Coser>()
        mAdapter = CoserAdapter(datas, R.layout.item_card_pic)
    }

    override fun getDatas(loadCache: Boolean) {
        var lastId: Int = 0
        if (pageIndex > 1) {
            lastId = datas?.get(datas!!.lastIndex)!!.id
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
