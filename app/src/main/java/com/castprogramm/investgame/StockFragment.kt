package com.castprogramm.investgame

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.castprogramm.investgame.EnumClasses.Error
import com.jjoe64.graphview.series.DataPoint


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
        var k: EditText = view.findViewById(R.id.quantity_stock)
        var image: ImageView = view.findViewById(R.id.icon_graph)
        var pr :TextView = view.findViewById(R.id.prquant)
        image.setImageResource(stock.companies?.r!!)
        bsold.setOnClickListener { v->
            var cent = 1
            if (k.text.isNotEmpty() )
                cent = k.text.toString().toInt()
            if (stockMarket.sold(stock, cent) == Error.EMPTYBAG){
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
            var cent = 1
            if (k.text.isNotEmpty() )
                cent = k.text.toString().toInt()
            when(stockMarket.buy(stock, cent)){
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
        var newtest1 : LiveData<MutableList<DataPoint>> = stock.costsofStock
        newtest1.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            pr.setText("Цена акции: "+ it.last().y.toString())
        })
        costGraphic.viewport.isScalable = true
        costGraphic.viewport.isScrollable = true
        costGraphic.gridLabelRenderer.horizontalAxisTitle = "Время"
        a?.testing?.objectsToUpdate?.add(costGraphic)
        a?.handler?.post(a?.testing)
        costGraphic.addStock(stock, this)
        var name : TextView = view.findViewById(R.id.namestock)
        name.setText(stock.companies?.n)
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

