package com.example.corpuscodetest.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.corpuscodetest.R
import com.example.corpuscodetest.adapter.HomePageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = HomePageAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            val tabView = layoutInflater.inflate(R.layout.tab_item, null)
            val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
            val tabText = tabView.findViewById<TextView>(R.id.tabText)


            when (position) {
                0 -> {
                    tabText.text = getString(R.string.home)
                    tabIcon.setImageResource(R.drawable.ic_home)
                }
                1 -> {
                    tabText.text = getString(R.string.about)
                    tabIcon.setImageResource(R.drawable.ic_about)
                }
                else -> {
                    tabText.text = ""
                    tabIcon.setImageResource(0)
                }
            }
            tab.customView = tabView
        }.attach()
    }
}