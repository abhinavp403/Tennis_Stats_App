package com.dev.abhinav.tennisstats

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var nameLeft: TextView
    private lateinit var nameRight: TextView
    private lateinit var MW1: TextView
    private lateinit var SW1: TextView
    private lateinit var GW1: TextView
    private lateinit var BPW1: TextView
    private lateinit var CS1: TextView
    private lateinit var SS1: TextView
    private lateinit var A1: TextView
    private lateinit var DB1: TextView
    private lateinit var MW2: TextView
    private lateinit var SW2: TextView
    private lateinit var GW2: TextView
    private lateinit var BPW2: TextView
    private lateinit var CS2: TextView
    private lateinit var SS2: TextView
    private lateinit var A2: TextView
    private lateinit var DB2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        nameLeft = findViewById(R.id.name1)
        nameRight = findViewById(R.id.name2)
        MW1 = findViewById(R.id.matches_won1)
        SW1 = findViewById(R.id.sets_won1)
        GW1 = findViewById(R.id.games_won1)
        BPW1 = findViewById(R.id.break_points_won1)
        CS1 = findViewById(R.id.clean_sets1)
        SS1 = findViewById(R.id.straight_sets1)
        A1 = findViewById(R.id.aces1)
        DB1 = findViewById(R.id.double_fault1)
        MW2 = findViewById(R.id.matches_won2)
        SW2 = findViewById(R.id.sets_won2)
        GW2 = findViewById(R.id.games_won2)
        BPW2 = findViewById(R.id.break_points_won2)
        CS2 = findViewById(R.id.clean_sets2)
        SS2 = findViewById(R.id.straight_sets2)
        A2 = findViewById(R.id.aces2)
        DB2 = findViewById(R.id.double_fault2)

        val yearArray = mutableListOf<Int>()
        for(i in 1968..2020) {
            yearArray.add(i)
        }
        val yearAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, yearArray)
        val actv3 = findViewById<View>(R.id.autoCompleteTextView3) as AutoCompleteTextView
        actv3.threshold = 1
        actv3.setAdapter(yearAdapter)
        actv3.setTextColor(Color.BLUE)

        val courtArray = listOf("Hard", "Clay", "Grass")
        val courtAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, courtArray)
        val actv4 = findViewById<View>(R.id.autoCompleteTextView4) as AutoCompleteTextView
        actv4.threshold = 1
        actv4.setAdapter(courtAdapter)
        actv4.setTextColor(Color.BLUE)

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
                actv1.threshold = 3
                actv1.setAdapter(adapter)
                actv1.setTextColor(Color.BLUE)
                val actv2 = findViewById<View>(R.id.autoCompleteTextView2) as AutoCompleteTextView
                actv2.threshold = 3
                actv2.setAdapter(adapter)
                actv2.setTextColor(Color.BLUE)
                button.setOnClickListener {
                    getATP(actv1, actv2, actv3, actv4)
                }
            }
            "wta" -> {
                val pythonFile = python.getModule("playerNames")
                val obj = pythonFile.callAttr("femalePlayers")
                val array = obj.toJava(Array::class.java)
                val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, array)
                val actv1 = findViewById<View>(R.id.autoCompleteTextView1) as AutoCompleteTextView
                actv1.threshold = 3
                actv1.setAdapter(adapter)
                actv1.setTextColor(Color.BLUE)
                val actv2 = findViewById<View>(R.id.autoCompleteTextView2) as AutoCompleteTextView
                actv2.threshold = 3
                actv2.setAdapter(adapter)
                actv2.setTextColor(Color.BLUE)
                button.setOnClickListener {
                    getWTA(actv1, actv2, actv3, actv4)
                }
            }
        }
    }

    private fun getATP(actv1: AutoCompleteTextView, actv2: AutoCompleteTextView, actv3: AutoCompleteTextView, actv4: AutoCompleteTextView) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("atp_script")
        val obj = pythonFile.callAttr("main", actv1.text.toString(), actv2.text.toString(), actv3.text.toString(), actv4.text.toString())
        if(obj.toString() == "Player Not Found") {
            Toast.makeText(this, "Player Not Found", Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "Enter Both Player Names") {
            Toast.makeText(this, "Enter Both Player Names", Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "No Record") {
            Toast.makeText(this, "No Record for " + actv3.text.toString(), Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "No Such Surface") {
            Toast.makeText(this, "No Such Surface", Toast.LENGTH_LONG).show()
        } else {
            val list = obj.asList()
            val leftScore = list[0].toString()
            val rightScore = list[1].toString()
            parseJSON(leftScore, "left")
            parseJSON(rightScore, "right")
            nameLeft.text = actv1.text.toString()
            nameRight.text = actv2.text.toString()
        }
    }

    private fun getWTA(actv1: AutoCompleteTextView, actv2: AutoCompleteTextView, actv3: AutoCompleteTextView, actv4: AutoCompleteTextView) {
        val python = Python.getInstance()
        val pythonFile = python.getModule("wta_script")
        val obj = pythonFile.callAttr("main", actv1.text.toString(), actv2.text.toString(), actv3.text.toString(), actv4.text.toString())
        if(obj.toString() == "Player Not Found") {
            Toast.makeText(this, "Player Not Found", Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "Enter Both Player Names") {
            Toast.makeText(this, "Enter Both Player Names", Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "No Record") {
            Toast.makeText(this, "No Record for " + actv3.text.toString(), Toast.LENGTH_LONG).show()
        } else if(obj.toString() == "No Such Surface") {
            Toast.makeText(this, "No Such Surface", Toast.LENGTH_LONG).show()
        } else {
            val list = obj.asList()
            val leftScore = list[0].toString()
            val rightScore = list[1].toString()
            parseJSON(leftScore, "left")
            parseJSON(rightScore, "right")
            nameLeft.text = actv1.text.toString()
            nameRight.text = actv2.text.toString()
        }
    }

    private fun parseJSON(score: String, side: String) {
        val jsonObj = JSONObject(score.substring(score.indexOf("{"), score.lastIndexOf("}") + 1))
        val matchesWon = jsonObj.getInt("matches-won")
        val setsWon = jsonObj.getInt("sets-won")
        val gamesWon = jsonObj.getInt("games-won")
        val breakptsWon = jsonObj.getInt("breakpts-won")
        val straightSets = jsonObj.getInt("clean-sets")
        val cleanSets = jsonObj.getInt("straight-sets")
        val aces = jsonObj.getInt("aces")
        val doubleFaults = jsonObj.getInt("doublefaults")
        when(side) {
            "left" -> {
                MW1.text = matchesWon.toString()
                SW1.text = setsWon.toString()
                GW1.text = gamesWon.toString()
                BPW1.text = breakptsWon.toString()
                SS1.text = straightSets.toString()
                CS1.text = cleanSets.toString()
                A1.text = aces.toString()
                DB1.text = doubleFaults.toString()
            }
            "right" -> {
                MW2.text = matchesWon.toString()
                SW2.text = setsWon.toString()
                GW2.text = gamesWon.toString()
                BPW2.text = breakptsWon.toString()
                SS2.text = straightSets.toString()
                CS2.text = cleanSets.toString()
                A2.text = aces.toString()
                DB2.text = doubleFaults.toString()
            }
        }
    }
}