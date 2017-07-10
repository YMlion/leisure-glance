package com.ymlion.leisure.module.video

import android.os.Bundle
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

    private var videoRv: RecyclerView? = null
    private var adapter: VideoAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_videos, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val root: View? = view
        if (root != null) {
            videoRv = root.findViewById(R.id.rv_videos) as RecyclerView?
        }
    }

    companion object {
        fun newInstance(): VideosFragment {
            return VideosFragment()
        }
    }
}
