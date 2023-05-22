package com.example.notificacion_sencilla_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numeroRandom:Int = (Math.random() * 100_001).toInt()
        Toast.makeText(this, "Número a recordar: $numeroRandom", Toast.LENGTH_LONG).show()

        comprobar.setOnClickListener(){
            if(numero.text.toString().toInt() == numeroRandom)
                             Toast.makeText(this, "Muy bien! has acertado en el numero",Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Algo mal :(, no has acertado en el numero",Toast.LENGTH_LONG).show()

        }
    }
}