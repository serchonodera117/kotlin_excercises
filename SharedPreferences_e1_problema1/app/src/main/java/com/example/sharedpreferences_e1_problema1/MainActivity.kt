package com.example.sharedpreferences_e1_problema1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        borrar.visibility = View.GONE

        val preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        var numAleatorio = (Math.random() * 51).toInt()
        var numIntentos = 0


        intentos.setText(preferencias.getString("noIntentos",""))

        verificar.setOnClickListener{
            var numUsuario = cajaTexto1.text.toString().toInt()
           if(numUsuario == numAleatorio)
           {
               Toast.makeText(this,"Felicidades has acertado!", Toast.LENGTH_LONG).show()
               borrar.visibility = View.VISIBLE
           }
           else
           {
               numIntentos = preferencias.getString("noIntentos","").toString().toInt()
               numIntentos++

               val editor = preferencias.edit()
               editor.putString("noIntentos",numIntentos.toString())
               editor.commit()


               if(numUsuario > numAleatorio)
                           Toast.makeText(this,"El valor aleatorio es menor que ${numUsuario}, " +
                                                            "intento ${numIntentos}", Toast.LENGTH_LONG).show()
               else if (numUsuario < numAleatorio)
                           Toast.makeText(this,"El valor aleatorio es mayor que ${numUsuario}," +
                                                             " intento ${numIntentos}", Toast.LENGTH_LONG).show()


               intentos.setText(preferencias.getString("noIntentos",""))
               cajaTexto1.setText("")

           }
        }

        borrar.setOnClickListener {
            val editar = preferencias.edit()
              editar.putString("noIntentos","0")
              editar.commit()

            numAleatorio = (Math.random() * 51).toInt()

            intentos.setText(preferencias.getString("noIntentos",""))
            cajaTexto1.setText("")
            borrar.visibility = View.GONE
        }
        }

    }

