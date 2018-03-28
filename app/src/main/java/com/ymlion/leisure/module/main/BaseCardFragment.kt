package com.ymlion.leisure.module.main

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ymlion.leisure.R
import com.ymlion.leisure.view.DividerItemDecoration
import com.ymlion.lib.base.RvBaseAdapter
import com.ymlion.lib.utils.DiffCallback
import com.ymlion.lib.utils.OnRvBottomListener
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * GankModel fragment
 */
abstract class BaseCardFragment<T> : TabFragment() {

    companion object {
        val PAGE_SIZE = 10
    }

    private var mDataRv: RecyclerView? = null
    protected var mAdapter: RvBaseAdapter<T>? = null
    private var mRefreshSr: SwipeRefreshLayout? = null
    protected var datas: MutableList<T>? = null

    protected var pageIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_common, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mDataRv = view?.findViewById(R.id.rv_list_common)
        mRefreshSr = view?.findViewById(R.id.srl_list_common)
        mRefreshSr?.setOnRefreshListener {
            pageIndex = 1
            Observable.timer(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe {
                        getDatas(false)
                    }
        }
    }

    private fun initData() {
        initAdapter()
        getDatas()
        initRv()
    }

    abstract fun initAdapter()

    private fun initRv() {
        val layoutManager = getLayoutManager()
        mDataRv?.layoutManager = layoutManager
        mDataRv?.addOnScrollListener(object : OnRvBottomListener() {
            override fun onBottom() {
                pageIndex++
                getDatas(false)
            }
        })
        mDataRv?.addItemDecoration(DividerItemDecoration(context, R.drawable.shape_rect_divider, DividerItemDecoration.BOTH_LIST))
        mDataRv?.adapter = mAdapter
    }

    open protected fun getLayoutManager():RecyclerView.LayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

    abstract fun getDatas(loadCache: Boolean = true)

    protected fun onLoadSuccess(list: MutableList<T>) {
        updateList(list)
        onRefreshComplete()
    }

    protected fun onRefreshComplete() {
        if (mRefreshSr?.isRefreshing as Boolean) {
            mRefreshSr?.isRefreshing = false
        }
    }

    private fun updateList(list: MutableList<T>) {
        if (pageIndex <= 1) {
            if (datas!!.isEmpty()) {
                datas!!.addAll(list)
                mAdapter?.notifyDataSetChanged()
            } else {
                val diffResult = DiffUtil.calculateDiff(DiffCallback<T>(list, datas), true)
                datas!!.clear()
                datas!!.addAll(list)
                diffResult.dispatchUpdatesTo(mAdapter)
            }
        } else {
            val num = mAdapter!!.itemCount
            datas!!.addAll(list)
            for (i in 0..list.size) {
                mAdapter!!.notifyItemInserted(num + i)
            }
            mAdapter!!.notifyItemRangeInserted(num, mAdapter!!.itemCount)
        }
    }
}
