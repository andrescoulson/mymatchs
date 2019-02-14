package com.andrescoulson.mymatchs.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.andrescoulson.mymatchs.R
import com.andrescoulson.mymatchs.fragment.FixtureFragment
import com.andrescoulson.mymatchs.fragment.ResultsFragment

class MainFragmentPageAdapter(val context: Context, val fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FixtureFragment()
            else -> ResultsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.fixture)
            else -> context.getString(R.string.results)
        }

    }
}