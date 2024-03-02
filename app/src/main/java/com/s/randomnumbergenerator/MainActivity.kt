package com.s.randomnumbergenerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    lateinit var yesOrNo: ConstraintLayout
    lateinit var conLayoutRandomWord: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yesOrNo = findViewById(R.id.yesOrNo)
        conLayoutRandomWord = findViewById(R.id.conLayoutRandomWord)

        yesOrNo.setOnClickListener {
            val intent = Intent(this, YesOrNoActivity::class.java)
            startActivity(intent)
        }
        conLayoutRandomWord.setOnClickListener {
            val intent = Intent(this, RandomWordActivity::class.java)
            startActivity(intent)
        }
    }
}

