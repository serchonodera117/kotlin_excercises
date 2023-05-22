package com.example.kotlin_peticioneshttp_librera_volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)


        val actionBar = supportActionBar
        actionBar?.hide()
    }
}