package com.ymlion.leisure.module.pic

import com.ymlion.leisure.R
import com.ymlion.leisure.data.model.GankModel
import com.ymlion.leisure.module.main.BaseCardFragment
import com.ymlion.leisure.module.pic.adapter.MeiziAdapter
import com.ymlion.leisure.net.Http
import com.ymlion.leisure.util.SubscriberAdapter

/**
 * GankModel fragment
 */
class MeiziFragment : BaseCardFragment<GankModel>() {

    companion object {
        fun newInstance(): MeiziFragment {
            return MeiziFragment()
        }
    }

    override fun initAdapter() {
        datas = mutableListOf<GankModel>()
        mAdapter = MeiziAdapter(datas, R.layout.item_card_pic)
    }

    override fun getDatas(loadCache: Boolean) {
        Http.build()
                .getMeizhis(PAGE_SIZE, pageIndex, loadCache)
                .subscribe(object : SubscriberAdapter<MutableList<GankModel>>() {
                    override fun onNext(t: MutableList<GankModel>?) {
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
