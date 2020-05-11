package com.castprogramm.investgame

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_dialog.view.*
import kotlin.math.min

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
        recycler.adapter = BrokerAdapter().apply {
            stocks = recStocks
        }

        var nameBro : TextView = ret.findViewById(R.id.name)
        var walletBro : TextView = ret.findViewById(R.id.wallet)
        var stockPriceBro: TextView = ret.findViewById(R.id.stockPrice)
        var minus : TextView = ret.findViewById(R.id.printminus)
        var newwallet: MutableLiveData<Double> = Broker.thisWallet
        var newles : MutableLiveData<Double> = Broker.thisLess
        walletBro.setText("Наличные:  " + wallet.toString())
        minus.setText("Текущий расход: " + expenditure.toString())
        newwallet.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            walletBro.setText("Наличные:  " + it.toString())
        })
        newles.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            minus.setText("Текущий расход: " + it.toString())
        })
        nameBro.setText("Имя: " + name)
        stockPriceBro.setText("Стоимость моих акций:  " + stockPrice.toString())
        var pi = LinearLayoutManager(ret.context)
        recycler.layoutManager = pi
        // Устанавливаем имя
        var adf = ret.findViewById<TextView>(R.id.name)
        adf.setOnClickListener{
            val mDialogView = LayoutInflater.from(ret.context).inflate(R.layout.login_dialog, null)
            val mBuilder = AlertDialog.Builder(ret.context)
                .setView(mDialogView)
                .setTitle("Авторизация")
            val  mAlertDialog = mBuilder.show()
            mDialogView.dialogLoginBtn.setOnClickListener {
                mAlertDialog.dismiss()
                val username = mDialogView.dialogNameEt.text.toString()
                // Запоминаем имя 3 ебаными способами, потому что по 1 не работает
                name = username
                Broker.name = username
                nameBro.text = username
            }
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
        return ret
    }
}