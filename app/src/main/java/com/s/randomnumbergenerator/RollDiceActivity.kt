package com.s.randomnumbergenerator

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import kotlin.random.Random


class RollDiceActivity : AppCompatActivity() {

    lateinit var imgDice: ImageView
    lateinit var imgBtn: ImageView
    lateinit var imgSound: ImageView
    lateinit var imgBack: ImageView
    private lateinit var images: IntArray
    private var currentIndex = 0
    private val interval: Long = 25
    private val totalDuration: Long = 1100
    private val handler = Handler()
    private var startTime: Long = 0
    private var mediaPlayer: MediaPlayer? = null
    var isTheDiceRolling: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roll_dice)

        imgDice = findViewById(R.id.imgDice)
        imgBtn = findViewById(R.id.imgBtn)
        imgSound = findViewById(R.id.imgSound)
        imgBack = findViewById(R.id.imgBack)

        mediaPlayer = MediaPlayer.create(this, R.raw.dice_rolling)

        var isSoundOn = false

        imgBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imgSound.setOnClickListener(){
            if(isSoundOn == true){
                isSoundOn = false
                imgSound.setImageResource(R.drawable.sound_off)
            }
            else{
                isSoundOn = true
                imgSound.setImageResource(R.drawable.sound_on)
            }
        }

        imgBtn.setOnClickListener(){
            if(!isTheDiceRolling){
                if(isSoundOn){
                    mediaPlayer = MediaPlayer.create(this, R.raw.dice_rolling)
                    mediaPlayer?.start()
                }
                isTheDiceRolling = true
                startImageChange()
            }
        }

        images = intArrayOf(
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
        )

    }

    private fun startImageChange() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                imgDice.setImageResource(images[currentIndex])

                currentIndex++
                if (currentIndex >= images.size) {
                    currentIndex = 0
                }

                if (System.currentTimeMillis() - startTime >= totalDuration) {
                    handler.removeCallbacks(this)
                    val randomNumber = Random.nextInt(1, 7)
                    imgDice.setImageResource(images[randomNumber - 1])
                    isTheDiceRolling = false
                    mediaPlayer?.release()
                    mediaPlayer = null

                } else {
                    handler.postDelayed(this, interval)
                }
            }
        }, interval)

        startTime = System.currentTimeMillis()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}
