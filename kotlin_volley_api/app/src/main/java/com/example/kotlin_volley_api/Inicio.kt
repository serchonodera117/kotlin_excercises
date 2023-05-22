package com.example.kotlin_volley_api

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