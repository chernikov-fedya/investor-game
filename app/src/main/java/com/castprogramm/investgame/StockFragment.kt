package com.castprogramm.investgame

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_broker.*


class StockFragment : Fragment() {
    var stock: Stock = Stock()

    var a = activity as MainActivity?
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_stock, container, false)
        var costGraphic : CostView = view.findViewById(R.id.graphic)
        var stockMarket = stockMarket()
        var bsold : Button = view.findViewById(R.id.sold)
        var bbuy : Button = view.findViewById(R.id.buy)
        bsold.setOnClickListener { v->
            if (stockMarket.sold(Broker, stock) == Error.EMPTYBAG){
                var texr = Error.EMPTYBAG.s
                var toast = Toast.makeText(this.activity, texr, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else{
                var text = "Yes"
                var toast =Toast.makeText(this.activity, text, Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
        bbuy.setOnClickListener { v->
            when(stockMarket.buy(Broker, stock)){
                Error.EMPTYMARKET -> {
                    var text = Error.EMPTYMARKET.s
                    var toast = Toast.makeText(this.activity, text, Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }

                Error.NOMONEY -> {
                    var text = Error.NOMONEY.s
                    var toast = Toast.makeText(this.activity, text, Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else ->{
                    var text = "Yes"
                    var toast =Toast.makeText(this.activity, text, Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }

        costGraphic.viewport.isScalable = true
        costGraphic.viewport.isScrollable = true
        costGraphic.gridLabelRenderer.verticalAxisTitle = "Цена"
        costGraphic.gridLabelRenderer.horizontalAxisTitle = "Время"
        a?.testing?.objectsToUpdate?.add(costGraphic)
        a?.handler?.post(a?.testing)
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

