package com.ymlion.leisure.module.pic

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.ymlion.leisure.R
import com.ymlion.leisure.module.main.TabFragment

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PicturesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PicturesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PicturesFragment : TabFragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var mPager: ViewPager? = null
    private var mAdapter: PagerAdapter? = null
    private var mTabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater!!.inflate(R.layout.fragment_pictures, container, false)
        mPager = root.findViewById(R.id.vp_picture)
        mTabLayout = root.findViewById(R.id.tab_picture)
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter = SectionsPagerAdapter(childFragmentManager)
        mPager!!.adapter = mAdapter
        mPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
        mTabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mPager))
    }

    fun onButtonPressed() {
        if (mListener != null) {
            mListener!!.onFragmentInteraction()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment PicturesFragment.
         */
        fun newInstance(): PicturesFragment {
            val fragment = PicturesFragment()
            return fragment
        }
    }

    class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var fragments = arrayOfNulls<Fragment>(2)

        override fun getItem(position: Int): Fragment {
            when(position) {
                0 -> {
                    if (fragments[0] == null) {
                        fragments[0] = MeiziFragment.newInstance()
                    }
                }
                1 -> {
                    if (fragments[1] == null) {
                        fragments[1] = CoserFragment.newInstance()
                    }
                }
            }
            return fragments[position]!!
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}
