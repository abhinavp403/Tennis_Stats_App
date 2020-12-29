package com.dev.abhinav.tennisstats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var cardView1: CardView
    private lateinit var cardView2: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        cardView1 = findViewById(R.id.cardview1)
        cardView2 = findViewById(R.id.cardview2)

        cardView1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("click", "atp")
            startActivity(intent)
        }

        cardView2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("click", "wta")
            startActivity(intent)
        }
    }
}