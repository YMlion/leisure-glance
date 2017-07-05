package com.ymlion.leisure.module.pic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ymlion.leisure.R

/**
 * A simple [Fragment] subclass.
 */
class MeiziFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_meizi, container, false)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(): MeiziFragment {
            return MeiziFragment()
        }
    }
}
