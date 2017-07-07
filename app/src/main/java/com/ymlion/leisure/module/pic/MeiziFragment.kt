package com.ymlion.leisure.module.pic

import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.Meizi
import com.ymlion.leisure.module.pic.adapter.MeiziAdapter
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter

/**
 * Meizi fragment
 */
class MeiziFragment : BasePicFragment<Meizi>() {

    companion object {
        fun newInstance(): MeiziFragment {
            return MeiziFragment()
        }
    }

    override fun initAdapter() {
        datas = mutableListOf<Meizi>()
        mAdapter = MeiziAdapter(datas, R.layout.item_card_pic)
    }

    override fun getDatas(loadCache: Boolean) {
        Http.build()
                .getMeizhis(PAGE_SIZE, pageIndex, loadCache)
                .subscribe(object : SubscriberAdapter<MutableList<Meizi>>() {
                    override fun onNext(t: MutableList<Meizi>?) {
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
}
