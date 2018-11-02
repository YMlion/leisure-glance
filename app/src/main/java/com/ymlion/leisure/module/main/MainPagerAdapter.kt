package com.ymlion.leisure.module.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ymlion.leisure.module.pic.PicturesFragment
import com.ymlion.leisure.module.video.VideosFragment

/**
 * Created by YMlion on 2017/7/5.
 */
class MainPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragments = arrayOfNulls<TabFragment>(3)

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                if (fragments[0] == null) {
                    fragments[0] = PicturesFragment.newInstance()
                }
            }
            1 -> {
                if (fragments[1] == null) {
                    fragments[1] = VideosFragment.newInstance()
                }
            }
            2 -> {
                if (fragments[2] == null) {
                    fragments[2] = TabFragment()
                }
            }
        }
        return fragments[position]!!
    }

    override fun getCount(): Int {
        return 3
    }
}