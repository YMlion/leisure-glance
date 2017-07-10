package com.ymlion.leisure.module.pic.adapter

import com.ymlion.leisure.data.model.GankModel

/**
 * Created by YMlion on 2017/7/7.
 */
class MeiziAdapter(list: MutableList<GankModel>?, layoutRes: Int): CardListAdapter<GankModel>(list, layoutRes) {

    override fun getUrl(model: GankModel): String {
        return model.url
    }

    override fun getTitle(model: GankModel): String {
        return model.desc
    }
}