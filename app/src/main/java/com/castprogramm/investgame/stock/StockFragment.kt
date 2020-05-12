package com.castprogramm.investgame.stock

import android.annotation.SuppressLint
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
import com.castprogramm.investgame.MainActivity
import com.castprogramm.investgame.R
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.fragment_stock.*

// Класс фрагментов, наследующийся от встроенного класса Fragment, для вывода акций и её параметров
class StockFragment : Fragment() {
    var stock: Stock = Stock()
    var counterSold: Int = 0 // Счётчик нажатий на кнопку продажи
    var counterBuy: Int = 0 // Счётчик нажатий на кнопку покупки
    var a = activity as MainActivity? // Наследование MainActivity
    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_stock, container, false)
        var costGraphic : CostView = view.findViewById(R.id.graphic)
        var stockMarket = StockMarket()
        var bsold : Button = view.findViewById(R.id.sold)
        var bbuy : Button = view.findViewById(R.id.buy)
        var k: EditText = view.findViewById(R.id.quantity_stock)
        var image: ImageView = view.findViewById(R.id.icon_graph)
        var pr :TextView = view.findViewById(R.id.prquant)
        var imageCountry : ImageView = view.findViewById(R.id.imagecountry)
        var text: TextView = view.findViewById(R.id.textView)
        text.setText(stock.companies?.opisanie)
        imageCountry.setImageResource(stock.companies?.country?.n!!)
        image.setImageResource(stock.companies?.r!!)
        bsold.setOnClickListener { v->
            counterSold = counterSold + 1 // обновляем счетчик нажатий
                var cent = 1 // Стандартное количество акций
                if (k.text.isNotEmpty()) // Проверка поля ввода на заполненность
                    cent = k.text.toString().toInt()
                if (stockMarket.sold(stock, cent) == Error.EMPTYBAG){ // Вывод сообщения, если у пользователя количество данных акций меньше того количества, что он хочет продать
                    var texr = Error.EMPTYBAG.s
                    var toast = Toast.makeText(this.activity, texr, 1000)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else{
                    if (counterSold < 2){ // Вывод сообщения при успехе
                        var text = "Продано"
                        var toast =Toast.makeText(this.activity, text, 1000)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        quantity_stock.setText("")
                    }
                }
        }
        bbuy.setOnClickListener { v->
            counterBuy = counterBuy + 1 // обновляем счетчик нажатий
            var cent = 1
            if (k.text.isNotEmpty()) // Проверка поля ввода на заполненность
                cent = k.text.toString().toInt()
            when(stockMarket.buy(stock, cent)){
                Error.EMPTYMARKET -> { // Вывод сообщения, если в магазине недостаточное количество акций
                    var text = Error.EMPTYMARKET.s
                    var toast = Toast.makeText(this.activity, text, 1000)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                Error.NOMONEY -> { // Вывод сообщения, если у пользователя недостаточное количество денег
                    var text = Error.NOMONEY.s
                    var toast = Toast.makeText(this.activity, text, 1000)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else ->{ // Вывод сообщения при успехе
                    if (counterBuy<2){
                        var text = "Куплено"
                        var toast =Toast.makeText(this.activity, text, 1000)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        quantity_stock.setText("")
                    }
                }
            }
        }
        var newtest1 : LiveData<MutableList<DataPoint>> = stock.costsofStock
        newtest1.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            pr.setText("$  "+ "%.2f".format(it.last().y))
        })
        costGraphic.viewport.isScalable = true // Разрешение скролла
        costGraphic.viewport.isScrollable = true
        costGraphic.gridLabelRenderer.horizontalAxisTitle = "Время" // Подпись оси Х у графика
        a?.testing?.objectsToUpdate?.add(costGraphic)
        a?.handler?.post(a?.testing) // Обновление
        costGraphic.addStock(stock, this) // Добавление нового значения на график
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