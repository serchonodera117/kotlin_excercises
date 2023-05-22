package com.example.kotlin_volley_api

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registrarme.*

class Registrarme : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarme)

        val actionBar = supportActionBar
        actionBar?.hide()

        button3.setOnClickListener {
            val queue = Volley.newRequestQueue(this)

            val url: String = "https://registrosappinventor.000webhostapp.com/myBase.php?usuario=${usuario.text}&contrasena=${contrasena.text}&nombre=${nombreCompleto.text}"

            val stringReq = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    var strResp = response.toString()
                    if (strResp=="")
                    {
                        Alerta("Usuarios","No se pudo registrar!")
                    }

                    else
                    {
                        val dialogBuilder = AlertDialog.Builder(this)
                        // set message of alert dialog
                        dialogBuilder.setMessage(strResp)
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton("OK", DialogInterface.OnClickListener {
                                    dialog, id -> finish()
                            })
                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("Registro de usuarios")
                        // show alert dialog
                        alert.show()
                    }
                },

                Response.ErrorListener {
                    Alerta("Red","Error de conexión")
                })
            queue.add(stringReq)
        }
    }
    fun Alerta(Titulo:String, Mensaje:String){
        val dialogBuilder = AlertDialog.Builder(this)
        // set message of alert dialog

        dialogBuilder.setMessage(Mensaje)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })


        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(Titulo)
        // show alert dialog
        alert.show()
    }

}
