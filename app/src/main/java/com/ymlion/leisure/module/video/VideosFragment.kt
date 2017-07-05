package com.ymlion.leisure.module.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ymlion.leisure.R
import com.ymlion.leisure.module.main.TabFragment

/**
 * A fragment representing a list of Items.
 */
class VideosFragment : TabFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_videos, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            val recyclerView = view
            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = VideoAdapter()
        }
        return view
    }
}
