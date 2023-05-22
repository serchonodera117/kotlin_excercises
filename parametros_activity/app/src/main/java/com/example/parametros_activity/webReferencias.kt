package com.example.parametros_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web_referencias.*

class webReferencias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_referencias)


        val bundle = intent.extras
        val dato = bundle?.getString("direccionURL")

        paginaWeb.loadUrl("http://${dato}")

        cerrar.setOnClickListener { finish() }
    }
}