package com.s.randomnumbergenerator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*
import kotlin.properties.Delegates

class YesOrNoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var imgBtn: ImageView
    private lateinit var txtTitle: TextView
    private lateinit var conLayout: ConstraintLayout
    private lateinit var imgGirl: ImageView
    private lateinit var txtAnswer: TextView
    private lateinit var tts: TextToSpeech
    private lateinit var imgSound: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yes_or_no)

        imgBtn = findViewById(R.id.imgBtn)
        txtTitle = findViewById(R.id.txtTitle)
        conLayout = findViewById(R.id.conLayout)
        imgGirl = findViewById(R.id.imgGirl)
        txtAnswer = findViewById(R.id.txtAnswer)
        imgSound = findViewById(R.id.imgSound)

        tts = TextToSpeech(this, this)

        val list = mutableListOf("yes", "no")
        var isSoundOn: Boolean = false

        imgSound.setOnClickListener(){
            if(isSoundOn){
                isSoundOn = false
                imgSound.setImageResource(R.drawable.sound_off)
            }
            else{
                isSoundOn = true
                imgSound.setImageResource(R.drawable.sound_on)
            }
        }

        imgBtn.setOnClickListener(){
            val randomAnswer = list.random()
            if(randomAnswer == "yes"){
                conLayout.setBackgroundColor(Color.parseColor("#085308"))
                imgGirl.visibility = View.GONE
                txtTitle.visibility = View.INVISIBLE
                txtAnswer.text = "Yes"
                txtAnswer.visibility = View.VISIBLE
                if(isSoundOn){
                    speakOut("Yes")
                }
            }
            else{
                conLayout.setBackgroundColor(Color.parseColor("#5A0909"))
                imgGirl.visibility = View.GONE
                txtTitle.visibility = View.INVISIBLE
                txtAnswer.text = "No"
                txtAnswer.visibility = View.VISIBLE
                if(isSoundOn){
                    speakOut("No")
                }

            }
            val scaleX = ObjectAnimator.ofFloat(txtAnswer, View.SCALE_X, 1.0f, 1.2f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(txtAnswer, View.SCALE_Y, 1.0f, 1.2f, 1.0f)
            val scaleAnimatorSet = AnimatorSet()
            scaleAnimatorSet.playTogether(scaleX, scaleY)
            scaleAnimatorSet.duration = 400
            scaleAnimatorSet.start()
            val scaleX2 = ObjectAnimator.ofFloat(imgBtn, View.SCALE_X, 1.0f, 1.01f, 1.0f)
            val scaleY2 = ObjectAnimator.ofFloat(imgBtn, View.SCALE_Y, 1.0f, 1.01f, 1.0f)
            val scaleAnimatorSet2 = AnimatorSet()
            scaleAnimatorSet2.playTogether(scaleX2, scaleY2)
            scaleAnimatorSet2.duration = 200
            scaleAnimatorSet2.start()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                // Handle error accordingly.
            }
        } else {
            // Initialization failed.
            // Handle error accordingly.
        }
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        if (tts.isSpeaking) {
            tts.stop()
        }
        tts.shutdown()
        super.onDestroy()
    }
}
