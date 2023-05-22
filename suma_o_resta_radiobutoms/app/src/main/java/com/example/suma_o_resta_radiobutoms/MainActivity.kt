package com.example.suma_o_resta_radiobutoms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ejecutar.setOnClickListener {
            if(sumar.isChecked){
                resultado.text ="Resultado =  ${n1.text.toString().toFloat() + n2.text.toString().toFloat()}"
            }
            if(restar.isChecked) {
                resultado.text = "Resultado =  ${n1.text.toString().toFloat() - n2.text.toString().toFloat()}"
            }
        }
    }
}