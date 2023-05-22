package com.example.kotlin_listview
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*

class Adaptador(private val context: Activity, private val title: Array<String>,
                private val description: Array<String>, private val imagid: Array<Int>):
                ArrayAdapter<String>(context, R.layout.custom_list,title){

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
         val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = title[position]
        imageView.setImageResource(imagid[position])
        subtitleText.text = description[position]

        return rowView
    }
  }