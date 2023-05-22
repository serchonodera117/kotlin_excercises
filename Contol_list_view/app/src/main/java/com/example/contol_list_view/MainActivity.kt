package com.example.contol_list_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val paises = arrayOf("México","Argentina", "Chile",
                              "Paraguay","Bolivia", "Perú",
                                "Colombia", "Uruguay")
        var numHabitantes = arrayOf(120_000_000, 50_000_000, 33_000_000,
                                     40_000_000, 20_000_000, 80_000_000,
                                      50_000_000, 25_000_000)

        val adaptor1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paises)
        list.adapter = adaptor1

        list.setOnItemClickListener { adapterView, view, i, l ->
            Indicador.text = "Población de: ${numHabitantes[i]}"
        }
    }
}