package com.example.sumar_dos_numeros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sumar.setOnClickListener {
            val num1 = valor1.text.toString().toFloat()
            val num2 = valor2.text.toString().toFloat()
            var suma = num1 + num2

            resultado.text = "Resultado: ${suma}"


        }
    }
}