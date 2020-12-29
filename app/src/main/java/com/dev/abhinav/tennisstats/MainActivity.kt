package com.dev.abhinav.tennisstats

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var nameLeft: TextView
    private lateinit var nameRight: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        nameLeft = findViewById(R.id.name1)
        nameRight = findViewById(R.id.name2)

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val python = Python.getInstance()

        when(intent.getStringExtra("click")) {
            "atp" -> {
                val pythonFile = python.getModule("playerNames")
                val obj = pythonFile.callAttr("malePlayers")
                val array = obj.toJava(Array::class.java)
                val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, array)
                val actv1 = findViewById<View>(R.id.autoCompleteTextView1) as AutoCompleteTextView
                actv1.threshold = 1
                actv1.setAdapter(adapter)
                actv1.setTextColor(Color.BLUE)
                val actv2 = findViewById<View>(R.id.autoCompleteTextView2) as AutoCompleteTextView
                actv2.threshold = 1
                actv2.setAdapter(adapter)
                actv2.setTextColor(Color.BLUE)
                button.setOnClickListener {
                    getATP(actv1, actv2)
                }
            }
        }
    }

    private fun getATP(actv1: AutoCompleteTextView, actv2: AutoCompleteTextView) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("atp_script")
        val obj = pythonFile.callAttr("main", actv1, actv2)
        val list = obj.asList()
        nameLeft.text = list[0].toString()
        nameRight.text = list[1].toString()
    }

    private fun getWTA() {
        val python = Python.getInstance()
        val pythonFile = python.getModule("wta_script")
        val obj = pythonFile.callAttr("main")
        val list = obj.asList()
        nameLeft.text = list[0].toString()
        nameRight.text = list[1].toString()
    }
}