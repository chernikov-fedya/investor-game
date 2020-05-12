package com.castprogramm.investgame.broker

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.castprogramm.investgame.R
import com.castprogramm.investgame.stock.BrokerAdapter
import com.castprogramm.investgame.stock.Stock
import kotlinx.android.synthetic.main.login_dialog.view.*

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

    var recStocks: MutableList<Stock> =  mutableListOf()
    var name = String()
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
                nameBro.text = username
            }
            // Слушатель для отмены AlertDialog'а
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
        var image : ImageView = ret.findViewById(R.id.photo)
        image.setOnClickListener {
            image.setBackgroundResource(R.drawable.rofl_anim)
            val animation = image.background
            if (animation is Animatable){
                animation.start()
            }
        }
        return ret
    }
}