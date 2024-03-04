package com.s.randomnumbergenerator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RandomWordActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var randomWordService: RandomWordService
    lateinit var textView: TextView
    lateinit var imgBtn: ImageView
    lateinit var imgBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_word)

        textView = findViewById(R.id.textView)
        imgBtn = findViewById(R.id.imgBtn)
        imgBack = findViewById(R.id.imgBack)

        retrofit = Retrofit.Builder()
            .baseUrl("https://random-word-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        randomWordService = retrofit.create(RandomWordService::class.java)

        imgBtn.setOnClickListener(){
            fetchRandomWord()
            val scaleX = ObjectAnimator.ofFloat(imgBtn, View.SCALE_X, 1.0f, 1.01f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(imgBtn, View.SCALE_Y, 1.0f, 1.01f, 1.0f)
            val scaleAnimatorSet = AnimatorSet()
            scaleAnimatorSet.playTogether(scaleX, scaleY)
            scaleAnimatorSet.duration = 200
            scaleAnimatorSet.start()
        }

        imgBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRandomWord() {
        val call = randomWordService.getRandomWord()
        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val words = response.body()
                    if (!words.isNullOrEmpty()) {
                        val randomWord = words[0]
                        Log.e("RandomWord", "Random Word: $randomWord")
                        val scaleX2 = ObjectAnimator.ofFloat(textView, View.SCALE_X, 1.0f, 1.05f, 1.0f)
                        val scaleY2 = ObjectAnimator.ofFloat(textView, View.SCALE_Y, 1.0f, 1.05f, 1.0f)
                        val scaleAnimatorSet2 = AnimatorSet()
                        scaleAnimatorSet2.playTogether(scaleX2, scaleY2)
                        scaleAnimatorSet2.duration = 400
                        scaleAnimatorSet2.start()
                        textView.text = randomWord
                    } else {
                        Log.e("RandomWord", "Response body is empty.")
                    }
                } else {
                    Log.e("RandomWord", "Failed to fetch random word: ${response.message()}")
                    textView.text = "Failed to fetch random word: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e("RandomWord", "Network error: ${t.message}")
                textView.text = "Network error: ${t.message}"
            }
        })
    }


    interface RandomWordService {
        @GET("/word")
        fun getRandomWord(@Query("number") number: Int = 1): Call<List<String>>
    }

}
