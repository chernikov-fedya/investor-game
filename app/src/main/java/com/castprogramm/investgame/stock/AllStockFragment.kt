package com.castprogramm.investgame.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.R
// Класс фрагментов, наследующийся от встроенного класса Fragment, для вывода списка всех акций
class AllStockFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        var ret = inflater.inflate(R.layout.fragment_all_stock, container, false) // Переменная для хранения файла разметки

        var recycler : RecyclerView = ret.findViewById(R.id.newaleksey) // Переменная для хранения RecyclerView

        recycler.adapter = StockAdapter() // Загрузка адаптера в RecyclerView

        var pi = LinearLayoutManager(ret.context) // Переменная для хранения менеджера макета
        recycler.layoutManager = pi  // Установка менеджера макета
        return ret
    }
}