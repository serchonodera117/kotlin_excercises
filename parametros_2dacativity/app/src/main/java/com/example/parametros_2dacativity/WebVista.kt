package com.example.parametros_2dacativity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web_vista.*

class WebVista : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_vista)
        val bundle = intent.extras
        val dato = bundle?.getString("direccionURL")

        paginaWeb.loadUrl("http://${dato}")

        cerrar.setOnClickListener {
            finish()
        }

    }

}