package com.example.checkbox_sumaresta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcular.setOnClickListener {
            var res = ""
            var res2 = ""


            if(restar.isChecked)
                res += "Resta = ${n1.text.toString().toFloat() - n2.text.toString().toFloat()}"

            resultado.text = res
        }
    }
}