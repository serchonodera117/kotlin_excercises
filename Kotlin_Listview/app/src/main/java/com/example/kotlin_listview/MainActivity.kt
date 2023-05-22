package com.example.kotlin_listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val language = arrayOf<String>("C","C++","Java",".Net","Kotlin","Ruby","Rails","Python","Java Script","Php","Ajax","Perl","Hadoop")
        val description = arrayOf<String>(
            "C programming is considered as the base for other programming languages",
            "C++ is an object-oriented programming language.",
            "Java is a programming language and a platform.",
            ".NET is a framework which is used to develop software applications.",
            "Kotlin is a open-source programming language, used to develop Android apps and much more.",
            "Ruby is an open-source and fully object-oriented programming language.",
            "Ruby on Rails is a server-side web application development framework written in Ruby language.",
            "Python is interpreted scripting  and object-oriented programming language.",
            "JavaScript is an object-based scripting language.",
            "PHP is an interpreted language, i.e., there is no need for compilation.",
            "AJAX allows you to send and receive data asynchronously without reloading the web page.",
            "Perl is a cross-platform environment used to create network and server-side applications.",
            "Hadoop is an open source framework from Apache written in Java."
        )
        val imageId = arrayOf<Int>(
            R.drawable.c_image,R.drawable.cpp_image,R.drawable.java_image,
            R.drawable.net_image,R.drawable.kotlin_image,R.drawable.ruby_image,
            R.drawable.rails_image,R.drawable.python_image,R.drawable.js_image,
            R.drawable.php_image,R.drawable.ajax_image,R.drawable.perl_image,
            R.drawable.hadoop_image
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myListAdapter = Adaptador(this, language, description, imageId)
        milistview.adapter = myListAdapter

        milistview.setOnItemClickListener(){adapterview,  view, position, id ->
            val titulo = adapterview.getItemAtPosition(position)
            val posicion = adapterview.getItemIdAtPosition(position)

            Toast.makeText(this, "Click on item at $titulo its item id $posicion", Toast.LENGTH_LONG).show()
        }



    }
}