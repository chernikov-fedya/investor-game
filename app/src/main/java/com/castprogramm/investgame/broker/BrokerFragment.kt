package com.castprogramm.investgame.broker

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.textclassifier.ConversationActions
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.EnumClasses.Companies
import com.castprogramm.investgame.R
import com.castprogramm.investgame.SplashActivity
import com.castprogramm.investgame.stock.BrokerAdapter
import com.castprogramm.investgame.stock.Stock
import com.castprogramm.investgame.stock.Stoks
import kotlinx.android.synthetic.main.fragment_broker.*
import kotlinx.android.synthetic.main.login_dialog.view.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

class BrokerFragment : Fragment() {

    companion object{
        fun newInstance(excer: MutableList<Stock>, name: String, wallet : Double, stockPrice: Double, expenditure: Double): BrokerFragment {
            var temp = BrokerFragment()
            temp.recStocks = excer
            temp.name = name
            temp.wallet = wallet
            temp.stockPrice = stockPrice
            temp.expenditure = expenditure
            return temp
        }
    }

    private fun showInfoPrompt(){
        val prefManagerBroker = PreferenceManager.getDefaultSharedPreferences(this.context)
        if (!prefManagerBroker.getBoolean("didShowBrokerPromp", false)){
        android.os.Handler().post(Runnable{
            MaterialTapTargetPrompt.Builder(this).setClipToView(null)
                .setTarget(info)
                .setPrimaryText("Здесь указаны все ваши данные")
                .setSecondaryText("Фото, имя, деньги, стоимость всех ваших акций, текущий расход")
                .setPromptFocal(RectanglePromptFocal())
                .setBackButtonDismissEnabled(false)
                .setPromptBackground(RectanglePromptBackground())
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
                        val prefEditor = prefManagerBroker.edit()
                        prefEditor.putBoolean("didShowBrokerPrompt", true)
                        prefEditor.apply()
                        showMinusInfo()
                    }
                }
                .show()
        })}
    }
    private fun showMinusInfo(){
        MaterialTapTargetPrompt.Builder(this).setClipToView(null)
            .setTarget(printminus)
            .setPrimaryText("Это ваш текущий расход")
            .setSecondaryText("Расход равен 1% от ваших общих средств (деньги + стоимость всех ваших акций)")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptBackground(RectanglePromptBackground())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED){
                        showStockPrompt()
                }
            }
            .show()
    }
    private fun showStockPrompt(){
        MaterialTapTargetPrompt.Builder(this)
            .setTarget(top)
            .setPrimaryText("Здесь будут ваши акции")
            .setPromptFocal(RectanglePromptFocal())
            .setPromptBackground(RectanglePromptBackground())
            .setPromptStateChangeListener { prompt, state ->
                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){

                }
            }
            .show()
    }

    var recStocks: MutableList<Stock> =  mutableListOf()
    var name : String = "Введите имя"
    var wallet : Double = 0.0
    var stockPrice: Double = 0.0
    var expenditure : Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var ret = inflater.inflate(R.layout.fragment_broker, container, false)
        var recycler : RecyclerView = ret.findViewById(R.id.rec)
        recycler.adapter = BrokerAdapter()
        var nameBro : TextView = ret.findViewById(R.id.name)
        var walletBro : TextView = ret.findViewById(R.id.wallet)
        var stockPriceBro: TextView = ret.findViewById(R.id.stockPrice)
        var minus : TextView = ret.findViewById(R.id.printminus)
        var newwallet: MutableLiveData<Double> = Broker.thisWallet
        var newles : MutableLiveData<Double> = Broker.thisLess
        var newSum : MutableLiveData<Double> = Broker.thisMyStock
        var end : MutableLiveData<Double> = Broker.thisEnd
        walletBro.setText("Наличные:  $" + "%.2f".format(wallet))
        minus.setText("Текущий расход: " + expenditure.toString())
        stockPriceBro.setText("Стоимость моих акций:  $" + "%.2f".format(stockPrice))
        newwallet.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            walletBro.setText("Наличные:  $" + "%.2f".format(it))
        })
        newles.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            minus.setText("Текущий расход: $" + it.toString())
        })
        newSum.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            stockPriceBro.setText("Стоимость моих акций:  $" + "%.2f".format(it))
        })
        nameBro.setText("Имя: " + name)
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        end.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it <= 0.0){
                var endDialog = LayoutInflater.from(ret.context).inflate(R.layout.fragment_end, null)
                var builder = AlertDialog.Builder(ret.context)
                    .setTitle("Конец игры")
                    .setMessage("Вы проиграли")
                var alertDialog = builder.show()
                alertDialog.show()
                Thread.sleep(1000)
                Broker.myStock.clear()
                Stoks.newsarray.clear()
                Broker.myStockCost = 0.0
                Broker.wallet = 10000.0
                Stoks.allStoks = mutableListOf(
                    Stock().apply { cost = 310.13; companies = Companies.Apple },
                    Stock().apply { cost = 59.62; companies = Companies.Intel},
                    Stock().apply { cost = 29.83; companies = Companies.Twitter},
                    Stock().apply { cost = 16.42; companies = Companies.Mailru},
                    Stock().apply { cost = 212.53; companies = Companies.Facebook},
                    Stock().apply { cost = 40.74; companies = Companies.Yandex},
                    Stock().apply { cost = 2379.4; companies = Companies.Amazon},
                    Stock().apply { cost = 282.7; companies = Companies.MasterCard},
                    Stock().apply { cost = 123.2; companies = Companies.IBM},
                    Stock().apply { cost = 203.33; companies = Companies.GazProm},
                    Stock().apply { cost = 4.83; companies = Companies.Lukoil},
                    Stock().apply { cost = 46.14; companies = Companies.CocaCola},
                    Stock().apply { cost = 181.67; companies = Companies.McDonalds},
                    Stock().apply { cost = 184.67; companies = Companies.Microsoft},
                    Stock().apply { cost = 3.01; companies = Companies.Huawei}
                )
                val intent = Intent(this.context, SplashActivity::class.java)
                startActivity(intent)
            }
        })

        // Находим поле отображения имени брокера по id и устанавливаем слушатель
        var namevalue = ret.findViewById<TextView>(R.id.name)
        namevalue.setOnClickListener{
            // Вызываем AlertDialog для заполнения имени
            val mDialogView = LayoutInflater.from(ret.context).inflate(R.layout.login_dialog, null)
            val mBuilder = AlertDialog.Builder(ret.context)
                .setView(mDialogView)
                .setTitle("Авторизация")
            val  mAlertDialog = mBuilder.show()
            // Устанавливаем слушатель на кнопку "авторизироваться" для ее обработки
            mDialogView.dialogLoginBtn.setOnClickListener {
                mAlertDialog.dismiss()
                val username = mDialogView.dialogNameEt.text.toString()
                // Присваиваем имя Брокеру
                name = username
                Broker.name = username
                nameBro.text = "Имя: $username"
            }
            // Слушатель для отмены AlertDialog'а
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        var image : ImageView = ret.findViewById(R.id.photo)
        showAnimation(image)
        image.setBackgroundResource(R.drawable.rofl_anim)
        image.setOnClickListener {
            showAnimation(image)
        }
        showInfoPrompt()
        return ret
    }
    fun showAnimation(image: ImageView){
        image.setBackgroundResource(R.drawable.rofl_anim)
        val animation = image.background
        if (animation is Animatable){
            animation.start()
        }

    }
}

class PreferenceBroker {
    companion object{
        val NAME: String = "key_NAME"
        val WALLET: String = "key_WALLET"
        fun save(context: Context){
            var editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(NAME, Broker.name)
            editor.putFloat(WALLET, Broker.wallet.toFloat())
            editor.apply()
        }
        fun load(context: Context){
            Broker.name = PreferenceManager.getDefaultSharedPreferences(context).getString(NAME, "")!!
            Broker.wallet = PreferenceManager.getDefaultSharedPreferences(context).getFloat(WALLET, 10000f).toDouble()

        }

    }
}
