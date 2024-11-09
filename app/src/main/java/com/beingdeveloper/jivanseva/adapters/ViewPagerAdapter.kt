package com.beingdeveloper.jivanseva.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beingdeveloper.jivanseva.ui.BusinessFragment
import com.beingdeveloper.jivanseva.ui.IncomeFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BusinessFragment()
            1 -> IncomeFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
