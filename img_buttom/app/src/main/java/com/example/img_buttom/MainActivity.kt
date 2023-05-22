package com.example.img_buttom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llamar.setBackgroundResource(R.mipmap.telefonollamar) //asignar rutas de las imagenes
        colgar.setBackgroundResource(R.mipmap.colgar)

        llamar.setOnClickListener{
            avisoLlamada.text = "Llamando . . ."
            llamar.setBackgroundResource(R.mipmap.telefono_llamando)
        }

        colgar.setOnClickListener{
            avisoLlamada.text = "Llamar"
            llamar.setBackgroundResource(R.mipmap.telefonollamar)
        }
    }


}