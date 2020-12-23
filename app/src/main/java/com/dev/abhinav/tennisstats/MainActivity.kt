package com.dev.abhinav.tennisstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {

    private lateinit var test: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val player1 = findViewById<EditText>(R.id.player1)
        val player2 = findViewById<EditText>(R.id.player2)
        test = findViewById(R.id.ttt)

        initPython()
    }

    private fun initPython() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
    }

    private fun getATP(): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("atp_script")
    }

    private fun getWTA(): String {
        val python = Python.getInstance()
        val pythonFile = python.getModule("atp_script")
    }
}