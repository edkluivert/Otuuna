package com.kluivert.otuuna.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kluivert.otuuna.ui.mainFrags.Dashboard
import com.kluivert.otuuna.ui.mainFrags.Events

class PagerAdapter(private val numOfPages: Int, fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = numOfPages

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> Events.newInstance()
        1 -> Dashboard.newInstance()
        else -> Fragment()
    }
}