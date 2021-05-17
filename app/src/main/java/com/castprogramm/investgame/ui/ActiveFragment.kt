package com.castprogramm.investgame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.castprogramm.investgame.R
import com.castprogramm.investgame.ViewPager2FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ActiveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_active, container, false)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.viewpager2)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        viewPager2.adapter = ViewPager2FragmentAdapter(this)
        TabLayoutMediator(tabs, viewPager2) { tab: TabLayout.Tab, i: Int ->
            when (i) {
                0 -> tab.text = "Акции"
//                1 -> tab.text = "Облигации"
            }
            viewPager2.setCurrentItem(i, true)
        }.attach()
        viewPager2.setCurrentItem(0, true)

        return view
    }
}