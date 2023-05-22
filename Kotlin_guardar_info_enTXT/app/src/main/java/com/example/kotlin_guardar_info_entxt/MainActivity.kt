package com.example.kotlin_guardar_info_entxt

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        guardar.setOnClickListener {
        var nombreArchivo  = fecha.text.toString().replace('/','-')
            try {
                val archivo = OutputStreamWriter(openFileOutput(nombreArchivo,Activity.MODE_PRIVATE))
                archivo.write(stickerNote.text.toString())
                archivo.flush()
                archivo.close()
            }catch (e: IOException){}
            Toast.makeText(this, "Los datos fueron grabados", Toast.LENGTH_SHORT).show()
            fecha.setText("")
            stickerNote.setText("")
        }

        recuperar.setOnClickListener {
            var nombreArchivo = fecha.text.toString().replace('/','-')

            if(fileList().contains(nombreArchivo))
            {
                try
                {
                    val archivo = InputStreamReader(openFileInput(nombreArchivo))
                    val br = BufferedReader(archivo)
                    var linea = br.readLine()
                    val todo = StringBuilder()

                    while (linea != null){
                        todo.append(linea + "\n")
                        linea = br.readLine()
                    }
                    br.close()
                    archivo.close()
                    stickerNote.setText(todo)
                }catch (e: IOException){}
            }
            else
            {
                Toast.makeText(this, "No hay datos grabados con esa fecha", Toast.LENGTH_LONG).show()
                stickerNote.setText("")
            }
        }
    }
}

