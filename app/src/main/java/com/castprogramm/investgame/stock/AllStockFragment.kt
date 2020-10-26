package com.castprogramm.investgame.stock

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.HelpApp
import com.castprogramm.investgame.R
import kotlinx.android.synthetic.main.stock_recycle.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

// Класс фрагментов, наследующийся от встроенного класса Fragment, для вывода списка всех акций
class AllStockFragment: Fragment() {
    companion object{
        val prefManagerAllStock = PreferenceManager.getDefaultSharedPreferences(HelpApp.globalContext)
    }
    var listStock = mutableListOf<Stock>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        android.os.Handler().post( Runnable {
            kotlin.run {
                if (!prefManagerAllStock.getBoolean("didShowAllStockPrompt", false)) {
                    MaterialTapTargetPrompt.Builder(this).setClipToView(null)
                        .setTarget(cardinal)
                        .setPrimaryText("Это окошко акции")
                        .setSecondaryText(
                            "Если вы нажмёте на неё, то вы узнаете о акции более подробно" + "\n" +
                                    "Нажмите на неё и перейдём к акции"
                        )
                        .setBackButtonDismissEnabled(true)
                        .setPromptFocal(RectanglePromptFocal())
                        .setPromptBackground(RectanglePromptBackground())
                        .setPromptStateChangeListener { prompt, state ->
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED
                            ) {
                                val prefEditor = prefManagerAllStock.edit()
                                prefEditor.putBoolean("didShowAllStockPrompt", true)
                                prefEditor.apply()
                            }
                        }
                        .show()
                }
            }
        } )

        var ret = inflater.inflate(R.layout.fragment_all_stock, container, false) // Переменная для хранения файла разметки

        var recycler : RecyclerView = ret.findViewById(R.id.newaleksey) // Переменная для хранения RecyclerView

        recycler.adapter = StockAdapter(listStock, this) // Загрузка адаптера в RecyclerView

        var pi = LinearLayoutManager(ret.context) // Переменная для хранения менеджера макета
        recycler.layoutManager = pi  // Установка менеджера макета
        return ret
    }
}