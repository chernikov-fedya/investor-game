package com.castprogramm.investgame

import android.content.Context
import android.graphics.Color
import android.graphics.Insets.add
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_stock.*
import kotlinx.android.synthetic.main.fragment_stock.view.*


class StockFragment : Fragment() {
    var stock: Stock = Stock()
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_stock, container, false)
        var costGraphic : CostView = view.findViewById(R.id.graphic)
        costGraphic.overScrollMode
        costGraphic.viewport.isScalable = true
        costGraphic.viewport.isScrollable = true
        costGraphic.viewport.scrollToEnd()
        costGraphic.viewport.computeScroll()
        costGraphic.viewport.xAxisBoundsStatus
        costGraphic.viewport.isXAxisBoundsManual = true
        costGraphic.viewport.isYAxisBoundsManual = false
        costGraphic.gridLabelRenderer.verticalAxisTitle = "Цена"
        costGraphic.gridLabelRenderer.horizontalAxisTitle = "Время"
        costGraphic.addStock(stock, this)
        var name : TextView = view.findViewById(R.id.namestock)
        name.setText(stock.name)
        return view
    }
    companion object{

        fun instfragment(temp: Stock): StockFragment {
            return StockFragment().apply {
                stock = temp
            }
        }
        }
    }

