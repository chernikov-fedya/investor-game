package com.castprogramm.investgame

//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Adapter
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewpager2.widget.ViewPager2
//import com.castprogramm.investgame.stock.Stock
//import com.castprogramm.investgame.stock.StockAdapter
//
//class PagerAdapter2(list: MutableList<MutableList<Stock>>):RecyclerView.Adapter<PagerAdapter2.Companion.PagerHolder>() {
//    var listAdapter = list
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHolder {
//        return PagerHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_all_stock, parent, false))
//    }
//
//    override fun getItemCount(): Int = listAdapter.size
//
//    override fun onBindViewHolder(holder: PagerHolder, position: Int) {
//        holder.bind(listAdapter[position])
//    }
//
//    companion object{
//        var frag: Fragment = Fragment()
//        class PagerHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//            var recyclerView : RecyclerView = itemView.findViewById(R.id.recactive)
//            fun bind(list: MutableList<Stock>){
//                recyclerView.adapter = StockAdapter().apply { listStock = list }
//                recyclerView.layoutManager = LinearLayoutManager(frag.context)
//            }
//        }
//    }
//}