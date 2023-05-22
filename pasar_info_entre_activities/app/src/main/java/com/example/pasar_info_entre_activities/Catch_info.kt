package com.example.pasar_info_entre_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_catch_info.*

class Catch_info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catch_info)

        val bundle = intent.extras
        val dato = bundle?.getString("direccionURL")

        paginaWeb.loadUrl("http://${dato}")

        cerrar.setOnClickListener{finish()}
    }
}