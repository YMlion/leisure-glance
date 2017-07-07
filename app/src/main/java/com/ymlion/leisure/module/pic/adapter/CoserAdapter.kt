package com.ymlion.leisure.module.pic.adapter

import com.ymlion.leisure.module.pic.model.Coser

/**
 * Created by YMlion on 2017/7/7.
 */
class CoserAdapter(list: MutableList<Coser>?, layoutRes: Int): CardListAdapter<Coser>(list, layoutRes) {

    override fun getUrl(model: Coser): String {
        return model.url!!
    }

    override fun getTitle(model: Coser): String {
        return model.title!!
    }
}