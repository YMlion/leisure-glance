package com.ymlion.leisure.module.main

import android.support.v4.app.Fragment
import android.view.animation.AnimationUtils
import com.ymlion.leisure.R

/**
 * base fragment:
 *
 * hide and display animation
 *
 * Created by YMlion on 2017/7/5.
 */
open class TabFragment: Fragment() {

    fun hideAnim() {
        val fadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        view?.startAnimation(fadeOut)
    }

    fun displayAnim() {
        val fadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        view?.startAnimation(fadeIn)
    }
}