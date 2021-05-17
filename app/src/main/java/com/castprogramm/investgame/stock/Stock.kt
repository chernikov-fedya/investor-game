package com.castprogramm.investgame.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jjoe64.graphview.series.DataPoint

import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.tools.Up
import com.castprogramm.investgame.broker.Broker
import com.castprogramm.investgame.database.DBDataPoint
import com.castprogramm.investgame.ui.StockFragment

// Класс для создания акции
@Entity
open class Stock: Up {
    @PrimaryKey
    var name = ""
    var cost: Double = 0.0 // Стоимость акции
    var companies: Companies? = null // Компания, к которой принадлежит акция
        set(value) {
            if (value != null)
                name = value.n
            field = value
        }
    @Ignore
    var costsofStock : MutableLiveData<MutableList<DBDataPoint>> = MutableLiveData() // LiveData, которая хранит в себе изменяемый список DataPoint
    @Ignore
    var costs : MutableList<DBDataPoint> = mutableListOf() // Изменяемый список, который присваивается в LiveData

    override fun update() { // Переопределение функции из интерфейса Up
        costs.add(DBDataPoint(costs.size.toDouble(), cost, this.name)) // Добавление в список новой серии значений
        costsofStock.value = costs // Присвоение в хранилище списка с сериями данных
    }

    override fun equals(other: Any?): Boolean =
        if (other is Stock)
            other.companies?.n == this.companies?.n
        else
            false
}
// Адаптер для вывода списка акиций в фрагмент активов с помощью RecyclerView
open class StockAdapter(list: MutableList<Stock>, var fragment: Fragment): RecyclerView.Adapter<StockViewHolder>() {
    var listStock = list

    fun setData(list: MutableList<Stock>){
        listStock = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val eee = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false) // Переменная для хранения файла разметки
        return StockViewHolder(eee, fragment)
    }
    override fun getItemId(position: Int): Long = position.toLong() //Функция, которая возращает Id
    override fun getItemCount(): Int = listStock.size // Функция, которая возварщает размер RecyclerView
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) { // Функция для отрисовки RecyclerView
        holder.bind(listStock[position])
    }
    override fun onViewDetachedFromWindow(holder: StockViewHolder) { // Функция для удаления Observer при условии, что объект находится вне пределов экрана
        holder.newCost?.removeObservers(fragment)
    }
}
class BrokerAdapter(var frag: Fragment): StockAdapter(Broker.myStock.keys.toMutableList(), frag){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrokerHolder {
        return BrokerHolder(LayoutInflater.from(parent.context).inflate(R.layout.stock_recycle, parent, false), frag)
    }
}
class BrokerHolder(view: View, fragment: Fragment): StockViewHolder(view, fragment){
    override fun bind(stock: Stock) {
        super.bind(stock)
        val quantity = itemView.findViewById<TextView>(R.id.quantity_stb)
        quantity.text = Broker.myStock[stock]!!.toString()
    }
}
open class StockViewHolder(itemView: View, var fragment: Fragment): RecyclerView.ViewHolder(itemView){// Переменная для передачи жизненного цикла фрагмента из MainActivity
    var name: TextView = itemView.findViewById(R.id.name_st) // Переменная для хранения TextView для вывода названия из файла разметки
    var cost: TextView = itemView.findViewById(R.id.cost_st) // Переменная для хранения TextView для вывода стоимости из файла разметки
    var cardView : CardView = itemView.findViewById(R.id.cardinal) // Переменная для хранения CardView для отрисовки нового фрагмента при нажатии из файла разметки
    var image: ImageView = itemView.findViewById(R.id.icon_comp) // Переменная для хранения ImageView для вывода эмблемы компании, которой принадлежит акция из файла разметки
    var newCost : MutableLiveData<MutableList<DBDataPoint>>? = null // Переменная для последующего присвоения в неё LiveData
    open fun bind(stock: Stock){ // Функция для отрисовки, принимает в себя акцию
        name.text = stock.companies?.n // Присвоение в поле названия акции
        cost.text = "$" + stock.cost.toString() // Присвоение в поле стоимости акции
        image.setImageResource(stock.companies?.r!!) // Присвоение в поле изображения эмблемы компании, которой принадлежит акция
        newCost = stock.costsofStock // Присвоение LiveData в переменную
        // С помощью паттерна Observer (наблюдатель) при обновлении значения в LiveData будет выполняться идущий ниже код
        newCost?.observe(fragment, androidx.lifecycle.Observer {
            cost.text = "$" + "%.2f".format(it.last().y) // Присвоение в поле стоимости акции
        }) // Функция для создания фрагмента при нажатии на CardView
        cardView.setOnClickListener {
            fragment.requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                .replace(R.id.frame_menu, StockFragment.instfragment(stock)) // Вставка фрагмента
                .addToBackStack(null) // Добавление фрагмента в BackStack
                .commit() // Сохраняет данные и информирует пользователя об успешности операции
        }
    }
}