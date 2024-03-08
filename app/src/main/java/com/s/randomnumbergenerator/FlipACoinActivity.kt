package com.s.randomnumbergenerator

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class FlipACoinActivity : AppCompatActivity() {

    lateinit var imgBtn: ImageView
    lateinit var imgCoin: ImageView
    lateinit var imgBack: ImageView
    lateinit var imgSound: ImageView
    var isSoundOn: Boolean = false
    var isCoinFlipping = false
    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flip_acoin)

        imgBtn = findViewById(R.id.imgBtn)
        imgCoin = findViewById(R.id.imgCoin)
        imgBack = findViewById(R.id.imgBack)
        imgSound = findViewById(R.id.imgSound)

        mediaPlayer = MediaPlayer.create(this, R.raw.coin_drop)

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
            if(isCoinFlipping == false){
                val randomNumber = (1..2).random()
                if(randomNumber == 1){
                    flipTheCoin(R.drawable.heads, "Heads")
                }
                else{
                    flipTheCoin(R.drawable.tails, "Tails")
                }
            }
        }
    }

    private fun flipTheCoin(imageId: Int, coinSide: String){
        if(isSoundOn){
            mediaPlayer = MediaPlayer.create(this, R.raw.coin_drop)
            mediaPlayer?.start()
        }
        isCoinFlipping = true
        imgCoin.setImageResource(R.drawable.coin)
        imgCoin.animate().apply {
            duration = 1000
            rotationYBy(1800f)
        }.withEndAction{
            mediaPlayer?.release()
            mediaPlayer = null
            isCoinFlipping = false
            imgCoin.setImageResource(imageId)
        }.start()
    }
}