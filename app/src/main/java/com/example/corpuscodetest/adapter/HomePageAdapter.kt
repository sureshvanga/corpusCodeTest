package com.example.corpuscodetest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.corpuscodetest.view.AboutFragment
import com.example.corpuscodetest.view.HomeFragment

class HomePageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> AboutFragment()
        else -> Fragment()
    }
}