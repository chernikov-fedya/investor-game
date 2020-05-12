package com.castprogramm.investgame.stock

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.castprogramm.investgame.Up
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
// Класс отрисовки графика, наследуемый от GraphView, с адапртацией под класс Stock
class CostView(context: Context, atrr: AttributeSet): GraphView(context, atrr), Up {
    init {
        this.viewport.setScalable(true) // Добавление возмозможности настройки масштаба по оси X и Y
        this.viewport.setScalableY(true)
    }
    // Функция для добавления DataPoint (серии значений (Date, Double) или (Double, Double)) в график
    fun addStock(stock: Stock, live:LifecycleOwner){
        var newPoint : LiveData<MutableList<DataPoint>> = stock.costsofStock // Копия LiveData из переменной stock
        // С помощью паттерна Observer (наблюдатель) при обновлении значения в LiveData будет выполняться идущий ниже код
        newPoint.observe(live, androidx.lifecycle.Observer { t ->
            var functiom = LineGraphSeries<DataPoint>(Array(t.size, { t[it] })) // Создание серии значений
            this.addSeries(functiom) // Добавление серии значений в график
            this.viewport.scrollToEnd() // Прокрутка графика в масимальное значение оси X
            this.viewport.setMinX(0.0) // Установление минимального значения на оси Х
        })
    }

    override fun update() { // Переопределение функции из интерфейса Up
        this.viewport.scrollToEnd() // Прокрутка графика в масимальное значение оси X
        this.viewport.setMinX(0.0) // Установление минимального значения на оси Х
        this.viewport.setMinY(0.0) // Установление минимального значения на оси Y
    }
}
