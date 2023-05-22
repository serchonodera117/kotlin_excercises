package com.example.almacenamiento_txt_en_sd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import java.io.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guardar.setOnClickListener {
            try{
                val tarjeta = getExternalFilesDir(null)
                val file = File(tarjeta?.absolutePath, et1.text.toString())
                val osw = OutputStreamWriter(FileOutputStream(file))

                osw.write(et2.text.toString())
                osw.flush()
                osw.close()

                Toast.makeText(this, "${et1.text.toString()} se ha guardado con éxito", Toast.LENGTH_SHORT).show()
                et1.setText("")
                et2.setText("")
            }catch (e: IOException){Toast.makeText(this, "No se ha podido guardar", Toast.LENGTH_SHORT).show()}
        }

        recuperar.setOnClickListener {
            val tarjeta = getExternalFilesDir(null)
            val file = File(tarjeta?.absolutePath, et1.text.toString())
            try {
                val fIn = FileInputStream(file)
                val archivo = InputStreamReader(fIn)
                val br = BufferedReader(archivo)
                var linea = br.readLine()
                val todo = StringBuilder()

                while(linea != null) {
                   todo.append(linea + "\n")
                    linea = br.readLine()
                }

                br.close()
                archivo.close()
                et2.setText(todo)
            }catch(e: IOException){ Toast.makeText(this, "No se pudo leer el archivo ${et1.text.toString()}", Toast.LENGTH_SHORT).show()}
            }
        }

    }
