package com.s.randomnumbergenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    lateinit var yesOrNo: ConstraintLayout
    lateinit var conLayoutRandomWord: ConstraintLayout
    lateinit var rollDice: ConstraintLayout
    lateinit var flipACoin: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yesOrNo = findViewById(R.id.yesOrNo)
        conLayoutRandomWord = findViewById(R.id.conLayoutRandomWord)
        rollDice = findViewById(R.id.rollDice)
        flipACoin = findViewById(R.id.flipACoin)

        yesOrNo.setOnClickListener {
            val intent = Intent(this, YesOrNoActivity::class.java)
            startActivity(intent)
        }
        conLayoutRandomWord.setOnClickListener {
            val intent = Intent(this, RandomWordActivity::class.java)
            startActivity(intent)
        }
        rollDice.setOnClickListener {
            val intent = Intent(this, RollDiceActivity::class.java)
            startActivity(intent)
        }
        flipACoin.setOnClickListener {
            val intent = Intent(this, FlipACoinActivity::class.java)
            startActivity(intent)
        }
    }
}

