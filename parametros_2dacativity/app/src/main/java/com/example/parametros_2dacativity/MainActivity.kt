package com.example.parametros_2dacativity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        consultar.setOnClickListener {
            if(miUrl.text.toString() != "")
            {
                Toast.makeText(this, "Conectando . . .", Toast.LENGTH_LONG).show()
                val navegar = Intent(this, WebVista::class.java)

                navegar.putExtra("direccionURL", miUrl.text.toString())
                startActivity(navegar)
            }else {
                Toast.makeText(this, "Url invalida, intente de nuevo", Toast.LENGTH_LONG).show()
            }
        }
    }
}