package com.castprogramm.investgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class Test(var text : TextView) : Up{
    var test = 0;
    override fun update() {
        test ++
        text.setText(test.toString())
    }
}
class MainActivity : AppCompatActivity() {
    var handler = Handler()
    var testing = Updater(handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var wow = Test(value)
        testing.objectsToUpdate.add(wow)
    handler.post(testing)

    }
}
