package com.example.tragaperras2020

import android.R
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.yesButton


class MainActivity : AppCompatActivity() {
    private var n: Int = 0
    private var n1: Int = 0
    private var n2: Int = 0
    private var puntuacion = 50
    private lateinit var frame3: AnimationDrawable
    private lateinit var frame: AnimationDrawable
    private lateinit var frame2: AnimationDrawable
    private var first = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.tragaperras2020.R.layout.activity_main)
        initFrame()
        button.setOnClickListener { btnStartOnClick() }

    }

    private fun reglas() {
        if(n1==0 && n==0 && n2==0){
            alert("Felicidades") {
                title = "Game Over"
                yesButton {  }
            }.show()
        }
        else if (n1==n2 && n1==n){
            puntuacion+=10

        }

        if (puntuacion==0){
            alert("Te has quedado sin coins") {
                title = "GameOver"
                yesButton {  }
            }.show()
            puntuacion=50
        }




    }

    private fun btnStartOnClick() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, com.example.tragaperras2020.R.raw.maquina)
        mediaPlayer.start()
        puntuacion -= 1
        tvCoins.setText(puntuacion.toString())
        ivSlot1.setImageResource(android.R.color.transparent)
        ivSlot.setImageResource(android.R.color.transparent)
        ivSlot2.setImageResource(android.R.color.transparent)
        frame.start()
        frame2.start()
        frame3.start()
        button.isEnabled = false
        doAsync {
            // Background
            SystemClock.sleep(1500)
            uiThread {
                // postexecute (puedo pintar)
                frame.stop()
                n1 = (Math.random()*6).toInt()
                var  id = resources.getIdentifier("ico$n1", "drawable", packageName)
                ivSlot1.setImageResource(id)
                doAsync {
                    SystemClock.sleep(1500)
                    uiThread {
                        frame2.stop()
                        n2 = (Math.random()*6).toInt()
                        id = resources.getIdentifier("ico$n2", "drawable", packageName)
                        ivSlot.setImageResource(id)
                        doAsync {
                            SystemClock.sleep(1500)
                            uiThread {
                                frame3.stop()
                                mediaPlayer.stop()

                                n = (Math.random()*6).toInt()
                                id = resources.getIdentifier("ico$n", "drawable", packageName)
                                ivSlot2.setImageResource(id)
                                reglas()
                                tvCoins.setText(puntuacion.toString())
                                button.isEnabled = true

                            }
                        }
                    }
                }

            }
        }


    }
    private fun initFrame() {

        ivSlot1.setBackgroundResource(com.example.tragaperras2020.R.drawable.ani2)
        ivSlot.setBackgroundResource(com.example.tragaperras2020.R.drawable.ani)
        frame2 = ivSlot.background as AnimationDrawable
        frame = ivSlot1.background as AnimationDrawable
        ivSlot2.setBackgroundResource(com.example.tragaperras2020.R.drawable.ani3)
        frame3 = ivSlot2.background as AnimationDrawable

    }
}