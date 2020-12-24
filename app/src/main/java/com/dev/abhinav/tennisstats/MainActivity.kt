package com.dev.abhinav.tennisstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {

    private lateinit var test: TextView
    private lateinit var button: Button
    private lateinit var nameLeft: TextView
    private lateinit var nameRight: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val player1 = findViewById<EditText>(R.id.player1)
        val player2 = findViewById<EditText>(R.id.player2)
        button = findViewById(R.id.button)
        nameLeft = findViewById(R.id.name1)
        nameRight = findViewById(R.id.name2)

        button.setOnClickListener {
            initPython()
        }
    }

    private fun initPython() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
//        val python = Python.getInstance()
//        val pythonFile = python.getModule("test")
//        val obj = pythonFile.callAttr("tt")
//        Log.d("aaa", obj.toString())
        //test.text = obj.toString()
        getATP()
    }

    private fun getATP() {
        val python = Python.getInstance()
        val pythonFile = python.getModule("atp_script")
        val obj = pythonFile.callAttr("main")
        val list = obj.asList()
        Log.d("aaa", list[0].toString())
        Log.d("aaa", list[1].toString())
        nameLeft.text = list[0].toString()
        nameRight.text = list[1].toString()
    }

    private fun getWTA() {
        val python = Python.getInstance()
        val pythonFile = python.getModule("wta_script")
    }
}