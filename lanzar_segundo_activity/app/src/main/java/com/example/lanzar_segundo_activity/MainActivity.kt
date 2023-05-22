package com.example.lanzar_segundo_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        acceder.setOnClickListener{
            if(contraseña.text.toString() == "abc123")
            {
                Toast.makeText(this, "bienvenido!", Toast.LENGTH_SHORT).show()
                val bienvenido  = Intent(this, bienvenido_screen::class.java)
                startActivity(bienvenido)
            }
            else{
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}