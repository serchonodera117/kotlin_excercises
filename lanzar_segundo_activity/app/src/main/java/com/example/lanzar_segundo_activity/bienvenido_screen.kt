package com.example.lanzar_segundo_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bienvenido_screen.*

class bienvenido_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenido_screen)

       regresar.setOnClickListener{
           val home = Intent(this, MainActivity::class.java)
           startActivity(home)
       }
    }
}