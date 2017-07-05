package com.ymlion.leisure.module.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewConfiguration
import android.widget.Toast
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager
import com.ymlion.leisure.R
import com.ymlion.leisure.base.BaseActivity
import com.ymlion.leisure.module.pic.PicturesFragment
import kotlinx.android.synthetic.main.activity_new_main.*

class NewMainActivity : BaseActivity(), MainContract.View, View.OnClickListener,
        AHBottomNavigation.OnTabSelectedListener, PicturesFragment.OnFragmentInteractionListener {
    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: AHBottomNavigationViewPager? = null
    private var mBottomNav: AHBottomNavigation? = null
    private var mAdapter: MainPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        mViewPager = view_pager
        mBottomNav = bottom_navigation
    }

    override fun initData() {
        val navAdapter = AHBottomNavigationAdapter(this, R.menu.tabs_main)
        navAdapter.setupWithBottomNavigation(mBottomNav)
        mBottomNav!!.isTranslucentNavigationEnabled = true
        mBottomNav!!.setOnTabSelectedListener(this)

        mAdapter = MainPagerAdapter(supportFragmentManager)
        mViewPager!!.adapter = mAdapter
        mViewPager!!.setCurrentItem(0, false)
    }

    override fun onClick(v: View) {
        when(v.id) {
        }
    }

    var mLastTapTime: Long = 0L

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        if (wasSelected) {
            val duration = (System.currentTimeMillis() - mLastTapTime).toInt()
            if (duration < ViewConfiguration.get(this).scaledDoubleTapSlop) {
                Toast.makeText(this, "selected $position $wasSelected + double click",
                        Toast.LENGTH_SHORT).show()
                // todo check whether refresh finished, if not then avoid refresh twice
                refreshUI()
                mLastTapTime = 0L
            } else {
                mLastTapTime += duration
            }
        } else {
            var lastTab: TabFragment  = mAdapter!!.getItem(mViewPager!!.currentItem) as TabFragment
            lastTab.hideAnim()
            mLastTapTime = 0L // first selected tab don't handle double click
            Toast.makeText(this, "selected $position $wasSelected", Toast.LENGTH_SHORT).show()
            mViewPager!!.setCurrentItem(position, false)
            lastTab = mAdapter!!.getItem(mViewPager!!.currentItem) as TabFragment
            lastTab.displayAnim()
        }

        return true
    }

    private fun refreshUI() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction() {

    }
}
