package com.ymlion.leisure.module

/**
* Created by YMlion on 2017/7/4.
*/
interface BaseView<in T> {
    fun setPresenter(presenter: T)
}