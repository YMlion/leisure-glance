package com.ymlion.leisure.module.main

import com.ymlion.leisure.module.BasePresenter
import com.ymlion.leisure.module.BaseView

/**
 * Created by YMlion on 2017/7/4.
 */
interface MainContract {

    interface Presenter: BasePresenter
    interface View: BaseView<Presenter>
}