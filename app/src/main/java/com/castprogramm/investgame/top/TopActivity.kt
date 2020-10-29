package com.castprogramm.investgame.top

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.lang.Exception


const val DATA_NAME = "DATA_NAME"
const val DATA_SCOPE = "DATA_SCOPE"

class TopActivity : AppCompatActivity() {

    val collectionDirectory = "recordsRow"
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var topList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)
        //отправка собственных данных
        send(loadDataRow(intent))

        //считывание всех данных
        db.collection(collectionDirectory)
            .limit(15)
            .orderBy("record", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                it.reversed()
                for (document in it) {
                    var temp = document.data.toMap()
                    topList.add("${temp.get("master")} : ${temp.get("record")}")
                }
            }
            .addOnCompleteListener {
                listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, topList)
            }
    }

    fun send(recordNote: RecordNote) {
        db.collection(collectionDirectory)
            .add(recordNote)
            .addOnSuccessListener {
                Log.d("NETWORKTOP", "SUCCESS")
            }
            .addOnFailureListener {
                throw Exception("NETWORKFAILTURE")
            }
    }

    fun loadDataRow(intent: Intent) = RecordNote(
        intent.getStringExtra(DATA_NAME) ?: throw NoDataException(),
        intent.getFloatExtra(DATA_SCOPE, 0f)
    )
}
