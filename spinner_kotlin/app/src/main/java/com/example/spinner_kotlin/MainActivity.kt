package com.example.spinner_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = arrayOf("Sumar","Restar","Multiplicar","Dividir")
        val adaptor1 = ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptor1

        button.setOnClickListener {
            when (spinner.selectedItem.toString()){
                "Sumar"-> resultado.text = "Resultado = ${n1.text.toString().toFloat() + n2.text.toString().toFloat()}"
                 "Restar" -> resultado.text = "Resultado = ${n1.text.toString().toFloat() - n2.text.toString().toFloat()}"
                "Multiplicar" -> resultado.text = "Restultado = ${n1.text.toString().toFloat() * n2.text.toString().toFloat()}"
                "Dividir" -> resultado.text = "Resultado = ${n1.text.toString().toFloat() / n2.text.toString().toFloat()}"

                else -> {resultado.text = "Resultado indefinido"}
            }
        }

    }
}