package com.castprogramm.investgame.ui

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.castprogramm.investgame.EnumClasses.Error
import com.castprogramm.investgame.HelpApp
import com.castprogramm.investgame.MainActivity
import com.castprogramm.investgame.R
import com.castprogramm.investgame.database.DBDataPoint
import com.castprogramm.investgame.stock.CostView
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.StockMarket
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.fragment_stock.*
import kotlinx.coroutines.Runnable
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

// Класс фрагментов, наследующийся от встроенного класса Fragment, для вывода акций и её параметров
class StockFragment : Fragment() {
    fun buildSoundPool(maxStreams: Int): SoundPool =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val attrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setAudioAttributes(attrs)
                .setMaxStreams(maxStreams)
                .build()
        } else {
            SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0)
        }

    fun showNamePrompt(){
        android.os.Handler().post(Runnable {
            if (!prefManagerStock.getBoolean("didShowStockPrompt", false))
            MaterialTapTargetPrompt.Builder(this).setClipToView(null)
                .setTarget(name_graph)
                .setPrimaryText("Это шапка графика")
                .setSecondaryText("Здесь указаны логотип компании, название компании и цена на данный момент")
                .setPromptFocal(RectanglePromptFocal())
                .setPromptBackground(RectanglePromptBackground())
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                        || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                        val prefEditor = prefManagerStock.edit()
                        prefEditor.putBoolean("didShowStockPrompt", true)
                        prefEditor.apply()

                        showGraphicPrompt()
                    }
                }
                .show()
        })
    }
    private fun showGraphicPrompt(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(graphic)
            .setPrimaryText("Это сам график")
            .setSecondaryText("Здесь вы можете увидеть, как цена акции изменялась на протяжении всей игры")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptBackground(RectanglePromptBackground())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                    showCountPrompt()
                }
            }
            .show()
    }
    private fun showCountPrompt(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(quantity_stock)
            .setPrimaryText("Это поле, где вы можете указать, какое количество акций вы хотите купить или продать")
            .setSecondaryText("Если вы ничего здесь не укажите, то будет продаваться или покупаться одна акция")
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                    showBuyPrompt()
                }
            }
            .show()
    }
    private fun showBuyPrompt(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(buy)
            .setPrimaryText("Это кнопка покупки")
            .setSecondaryText("Если ты нажмешь на неё, то купишь одну акцию или количество указанных акций")
            .setBackButtonDismissEnabled(true)
            .setPromptFocal(RectanglePromptFocal())
            .setPromptBackground(RectanglePromptBackground())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                    || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                    showSoldPrompt()
                }
            }
            .show()

    }
    private fun showSoldPrompt(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(sold)
            .setPrimaryText("Это кнопка продажи")
            .setSecondaryText("Если ты нажмешь на неё, то продашь одну акцию или количество указанных акций")
            .setBackButtonDismissEnabled(true)
            .setPromptBackground(RectanglePromptBackground())
            .setPromptFocal(RectanglePromptFocal())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED ||
                            state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED){
                    showTextPrompt()
                }
            }
            .show()
    }
    private fun showTextPrompt(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(textgraphic)
            .setPrimaryText("Здесь находится информация о компании, которой принадлежит акция")
            .setSecondaryText("Тут можно увидеть флаг страны, где находится компания, название и описание компании")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptBackground(RectanglePromptBackground())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                    state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){

                }
            }
            .show()
    }

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
        var sd = activity?.assets?.openFd("zvon.mp3")
        val soundPool: SoundPool = buildSoundPool(10)
        val zvon = soundPool.load(sd, 1)
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
        showNamePrompt()
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
                    soundPool.play(zvon, 1f, 1f, 1, 0, 1f)
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
                    soundPool.play(zvon, 1f, 1f, 1, 0, 1f)
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
        val newtest1 : LiveData<MutableList<DBDataPoint>> = stock.costsofStock
        newtest1.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            pr.setText("$  "+ "%.2f".format(it.last().y))
        })
        costGraphic.viewport.isScalable = true // Разрешение скролла
        costGraphic.viewport.isScrollable = true
        costGraphic.gridLabelRenderer.horizontalAxisTitle = "Время" // Подпись оси Х у графика
        a?.testing?.objectsToUpdate?.add(costGraphic)
        a?.handler?.post(a?.testing!!) // Обновление
        costGraphic.addStock(stock, this) // Добавление нового значения на график
        val name : TextView = view.findViewById(R.id.namestock)
        name.setText(stock.companies?.n)
        return view
    }

    companion object{
        val prefManagerStock = PreferenceManager.getDefaultSharedPreferences(HelpApp.globalContext)
        fun instfragment(temp: Stock): StockFragment {
            return StockFragment().apply {
                stock = temp
            }
        }
        }
    }