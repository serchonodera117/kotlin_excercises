package com.example.check_box_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcular.setOnClickListener {
            var res = ""
            if(sumar.isChecked){
                res = "suma = ${num1.text.toString().toFloat() + num2.text.toString().toFloat()}"
            }
            if(restar.isChecked){
                res += " resta = ${num1.text.toString().toFloat() - num2.text.toString().toFloat()}"
            }
            resultado.text = res
        }
    }
}