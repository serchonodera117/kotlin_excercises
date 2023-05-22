package com.example.examen2p

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_registro_cliente.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegistroCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_registro_cliente)

        val actionBar = supportActionBar
        actionBar?.hide()

        //obtención de correo
        val bundle = intent.extras
        val correo = bundle?.getString("correoLlave")


//adapter
        val municipios = arrayOf("Colima", "Villa de Alvarez")
        val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_gallery_item,municipios)
        editaClienteMunicipio.adapter = adaptador

        cometerEdicionCliente.setOnClickListener {

            val queue = Volley.newRequestQueue(this)
            val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_agregarclientes.php?correoVendedor=${correo}" +
                    "&NombreCliente=${editaClienterNombre.text}&ApellidosCliente=${editaClienteApellidos.text}" +
                    "&ImagenCliente=${editaClienteImagen.text}&MunicipioCliente=${editaClienteMunicipio.selectedItem.toString()}" +
                    "&DireccionCliente=${editaClienteDomicilio.text}&CorreoCliente=${editaClienteCorreo.text}" +
                    "&TipoUsuario=Cliente&TelefonoCliente=${editaClienteTelefono.text}"

            val stringReq = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    var strResp = response.toString()
                    if (strResp=="")
                    {
                        Alerta("Usuarios","No se pudo registrar!")

                    }

                    else
                    {     //mensaje de respuesta al registrar

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setMessage(strResp)
                            .setCancelable(false)
                            .setPositiveButton("OK", DialogInterface.OnClickListener {
                                    dialog, id -> finish()
                            })

                        val alert = dialogBuilder.create()
                        alert.setTitle("Registro de usuarios")
                        alert.show()

                    }
                },

                Response.ErrorListener {
                    Alerta("Red","Error de conexión")

                })
            queue.add(stringReq)
        }

        quitarEdicion.setOnClickListener { finish() }


    }

    fun Alerta(Titulo:String, Mensaje:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(Mensaje)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(Titulo)
        alert.show()
    }

}