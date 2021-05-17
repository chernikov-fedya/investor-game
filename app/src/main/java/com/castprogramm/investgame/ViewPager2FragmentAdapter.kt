package com.castprogramm.investgame

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.castprogramm.investgame.ui.AllStockFragment
import com.castprogramm.investgame.stock.Stoks

class ViewPager2FragmentAdapter(activity: Fragment):FragmentStateAdapter(activity) {
    var typeOfActive = mutableListOf(Stoks.allStoks
//        , Stoks.allObligation
    )
    override fun getItemCount(): Int = typeOfActive.size

    override fun createFragment(position: Int): Fragment = AllStockFragment().apply {
        listStock = typeOfActive[position]
        }
}