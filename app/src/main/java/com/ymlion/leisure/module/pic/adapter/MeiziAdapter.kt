package com.ymlion.leisure.module.pic.adapter

import com.ymlion.leisure.data.model.Meizi

/**
 * Created by YMlion on 2017/7/7.
 */
class MeiziAdapter(list: MutableList<Meizi>?, layoutRes: Int): CardListAdapter<Meizi>(list, layoutRes) {

    override fun getUrl(model: Meizi): String {
        return model.url
    }

    override fun getTitle(model: Meizi): String {
        return model.desc
    }
}