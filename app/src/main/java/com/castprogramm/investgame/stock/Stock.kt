package com.castprogramm.investgame.stock

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint

import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.Up
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.broker.BrokerFragment

// Класс для создания акции
open class Stock: Up {
    var cost: Double = 0.0 // Стоимость акции
    var companies: Companies? = null // Компания, к которой принадлежит акция
    var costsofStock : MutableLiveData<MutableList<DataPoint>> = MutableLiveData() // LiveData, которая хранит в себе изменяемый список DataPoint
    var costs : MutableList<DataPoint> = mutableListOf() // Изменяемый список, который присваивается в LiveData

    override fun update() { // Переопределение функции из интерфейса Up
        costs.add(DataPoint(costs.size.toDouble(), cost)) // Добавление в список новой серии значений
        costsofStock.value = costs // Присвоение в хранилище списка с сериями данных
    }

    override fun equals(other: Any?): Boolean =
        if (other is Stock)
            other.companies?.n == this.companies?.n
        else
            false
}
// Адаптер для вывода списка акиций в фрагмент активов с помощью RecyclerView
class StockAdapter(list: MutableList<Stock>): RecyclerView.Adapter<StockAdapter.Companion.StockViewHolder>(){
    var listStock = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false) // Переменная для хранения файла разметки
        return StockViewHolder(eee)
    }
    override fun getItemId(position: Int): Long = position.toLong() //Функция, которая возращает Id
    override fun getItemCount(): Int = listStock.size // Функция, которая возварщает размер RecyclerView
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) { // Функция для отрисовки RecyclerView
        holder.bind(listStock[position])
    }
    override fun onViewDetachedFromWindow(holder: StockViewHolder) { // Функция для удаления Observer при условии, что объект находится вне пределов экрана
        holder.newCost?.removeObservers(fragment!!)
    }
    companion object{
        var fragmentManager : FragmentManager? = null // Переменная для передачи supportFragmentManager из MainActivity
        var fragment: AllStockFragment? = null // Переменная для передачи жизненного цикла фрагмента из MainActivity
        class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_st) // Переменная для хранения TextView для вывода названия из файла разметки
            var cost: TextView = itemView.findViewById(R.id.cost_st) // Переменная для хранения TextView для вывода стоимости из файла разметки
            var cardView : CardView = itemView.findViewById(R.id.cardinal) // Переменная для хранения CardView для отрисовки нового фрагмента при нажатии из файла разметки
            var image: ImageView = itemView.findViewById(R.id.icon_comp) // Переменная для хранения ImageView для вывода эмблемы компании, которой принадлежит акция из файла разметки
            var newCost : MutableLiveData<MutableList<DataPoint>>? = null // Переменная для последующего присвоения в неё LiveData
            fun bind(stock: Stock){ // Функция для отрисовки, принимает в себя акцию
                name.setText(stock.companies?.n) // Присвоение в поле названия акции
                cost.setText("$" + stock.cost.toString()) // Присвоение в поле стоимости акции
                image.setImageResource(stock.companies?.r!!) // Присвоение в поле изображения эмблемы компании, которой принадлежит акция
                newCost = stock.costsofStock // Присвоение LiveData в переменную
                // С помощью паттерна Observer (наблюдатель) при обновлении значения в LiveData будет выполняться идущий ниже код
                newCost?.observe(fragment!!, androidx.lifecycle.Observer {
                    cost.setText("$" + "%.2f".format(it.last().y)) // Присвоение в поле стоимости акции
                })
                // Функция для создания фрагмента при нажатии на CardView
                cardView.setOnClickListener {
                    val fm = fragmentManager // Присвоение supportFragmentManager из MainActivity
                    val ft = fm?.beginTransaction()
                    var f = StockFragment.instfragment(stock) // Создание StockFragment
                    ft?.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                    ft?.replace(R.id.frame_menu, f) // Вставка фрагмента
                    ft?.addToBackStack(null) // Добавление фрагмента в BackStack
                    ft?.commit() // Сохраняет данные и информирует пользователя об успешности операции
                }
            }
        }
    }
}
class BrokerAdapter(): RecyclerView.Adapter<BrokerAdapter.Companion.BrokerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrokerViewHolder {
        var eee = LayoutInflater.from(parent.context).inflate(R.layout.broker_recycle, parent, false) // Переменная для хранения файла разметки
        return BrokerViewHolder(eee)
    }
    override fun getItemId(position: Int): Long = position.toLong() //Функция, которая возращает Id
    override fun getItemCount(): Int = Broker.myStock.size // Функция, которая возварщает размер RecyclerView
    override fun onBindViewHolder(holder: BrokerViewHolder, position: Int) { // Функция для отрисовки RecyclerView
        holder.bind(Broker.myStock.keys.toMutableList()[position])
    }
    override fun onViewDetachedFromWindow(holder: BrokerViewHolder) { // Функция для удаления Observer при условии, что объект находится вне пределов экрана
        holder.newCostb?.removeObservers(fragment!!)
    }
    companion object{ // Статические поля
        var fragmentManager : FragmentManager? = null // Переменная для передачи supportFragmentManager из MainActivity
        var fragment: BrokerFragment? = null // Переменная для передачи жизненного цикла фрагмента из MainActivity
        class BrokerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var name: TextView = itemView.findViewById(R.id.name_stb) // Переменная для хранения TextView для вывода названия акции из файла разметки
            var cost: TextView = itemView.findViewById(R.id.cost_stb) // Переменная для хранения TextView для вывода стоимости акции из файла разметки
            var cardView : CardView = itemView.findViewById(R.id.cardinalb) // Переменная для хранения CardView для отрисовки нового фрагмента при нажатии из файла разметки
            var image: ImageView = itemView.findViewById(R.id.icon_compb) // Переменная для хранения ImageView для вывода эмблемы компании, которой принадлежит акция из файла разметки
            var quantity : TextView = itemView.findViewById(R.id.quantity_stb) // Переменная для хранения TextView для вывода количества акции из файла разметки
            var newCostb : MutableLiveData<MutableList<DataPoint>>? = null // Переменная для последующего присвоения в неё LiveData
            fun bind(stock: Stock){ // Функция для отрисовки, принимает в себя акцию
                name.setText(stock.companies?.n) // Присвоение в поле названия акции
                cost.setText("$" + "%.2f".format(stock.cost)) // Присвоение в поле стоимости акции
                image.setImageResource(stock.companies?.r!!) // Присвоение в поле изображения эмблемы компании, которой принадлежит акция
                newCostb = stock.costsofStock // Присвоение LiveData в переменную
                quantity.setText(Broker.myStock[stock]!!.toString()) // Присвоение количества акции

                // С помощью паттерна Observer (наблюдатель) при обновлении значения в LiveData будет выполняться идущий ниже код
                newCostb?.observe(fragment!!, androidx.lifecycle.Observer {
                    cost.setText("$" +"%.2f".format(it.last().y)) // Присвоение в поле стоимости акции
                })
                // Функция для создания фрагмента при нажатии на CardView
                cardView.setOnClickListener {
                    val fm = fragmentManager // Присвоение supportFragmentManager из MainActivity
                    val ft = fm?.beginTransaction()
                    var f = StockFragment.instfragment(stock) // Создание StockFragment
                    ft?.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                    ft?.replace(R.id.frame_menu, f) // Вставка фрагмента
                    ft?.addToBackStack(null) // Добавление фрагмента в BackStack
                    ft?.commit() // Сохраняет данные и информирует пользователя об успешности операции
                }
            }
        }
    }
}