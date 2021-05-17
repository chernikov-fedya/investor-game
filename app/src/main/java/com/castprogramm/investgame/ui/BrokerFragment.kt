package com.castprogramm.investgame.broker

import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.ui.EndFragment
import com.castprogramm.investgame.HelpApp
import com.castprogramm.investgame.R
import com.castprogramm.investgame.stock.BrokerAdapter
import com.castprogramm.investgame.stock.Stock
import kotlinx.android.synthetic.main.fragment_broker.*
import kotlinx.android.synthetic.main.login_dialog.view.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal

class BrokerFragment : Fragment() {

    companion object{
        val prefManagerBroker = PreferenceManager.getDefaultSharedPreferences(HelpApp.globalContext)
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
        if (!prefManagerBroker.getBoolean("didShowBrokerPrompt", false)){
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
        val ret = inflater.inflate(R.layout.fragment_broker, container, false)
        val recycler : RecyclerView = ret.findViewById(R.id.rec)
        val brokerAdapter = BrokerAdapter(this)
        Broker.liveDataMyStock.observe(viewLifecycleOwner, {
            brokerAdapter.setData(it.keys.toMutableList())
        })

        recycler.adapter = brokerAdapter
        val nameBro : TextView = ret.findViewById(R.id.name)
        val walletBro : TextView = ret.findViewById(R.id.wallet)
        val stockPriceBro: TextView = ret.findViewById(R.id.stockPrice)
        val minus : TextView = ret.findViewById(R.id.printminus)

        val newwallet: MutableLiveData<Double> = Broker.thisWallet
        val newles : MutableLiveData<Double> = Broker.thisLess
        val newSum : MutableLiveData<Double> = Broker.thisMyStock

        walletBro.setText("Наличные:  $" + "%.2f".format(wallet))
        minus.setText("Текущий расход: " + expenditure.toString())
        stockPriceBro.setText("Стоимость моих акций:  $" + "%.2f".format(stockPrice))

        newwallet.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            walletBro.setText("Наличные:  $" + "%.2f".format(it))
            if (it <= 0.0){
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_menu, EndFragment())
                    .commit()
            }
        })
        newles.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            minus.setText("Текущий расход: $" + it.toString())
        })
        newSum.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            stockPriceBro.setText("Стоимость моих акций:  $" + "%.2f".format(it))
        })
        nameBro.setText("Имя: " + name)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Находим поле отображения имени брокера по id и устанавливаем слушатель
        val namevalue = ret.findViewById<TextView>(R.id.name)
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

        val image : ImageView = ret.findViewById(R.id.photo)
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
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putString(NAME, Broker.name)
            editor.putFloat(WALLET, Broker.wallet.toFloat())
            editor.apply()
        }
        fun load(context: Context){
            Broker.name = PreferenceManager.getDefaultSharedPreferences(context).getString(NAME, "Нажмите, чтобы ввести")!!
            Broker.wallet = PreferenceManager.getDefaultSharedPreferences(context).getFloat(WALLET, 10000f).toDouble()

        }

    }
}
