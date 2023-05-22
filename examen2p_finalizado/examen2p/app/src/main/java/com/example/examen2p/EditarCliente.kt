package com.example.examen2p

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.Fragmentos.MisClientes_f
import kotlinx.android.synthetic.main.activity_editar_cliente.*
import kotlinx.android.synthetic.main.activity_registro_cliente.*

class EditarCliente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cliente)

        val municipios = arrayOf("Colima", "Villa de Alvarez")
        val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_gallery_item,municipios)
        editaMunicipio.adapter = adaptador

        val bunle = intent.extras
        var cliente = bunle?.getParcelable<DatosCliente>("datosCliente")

        var posicionMunicipio = if(cliente?.MunicipioClientee == "Colima") 0 else 1

        editaNombre.setText(cliente?.NombreCliente)
        editaApellidos.setText(cliente?.ApellidosCliente)
        editaImagen.setText(cliente?.ImagenCliente)
        editaMunicipio.setSelection(posicionMunicipio)

        editaDomicilio.setText(cliente?.DireccionCliente)
        editaCorreo.setText(cliente?.CorreoCliente)
        editaTelefono.setText(cliente?.TelefonoCliente)






        guardarEdicion.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_editarclientes.php?idcliente=${cliente?.idcliente}"+
                    "&NombreCliente=${editaNombre.text}&ApellidosCliente=${editaApellidos.text}" +
                    "&ImagenCliente=${editaImagen.text}&MunicipioCliente=${editaMunicipio.selectedItem.toString()}" +
                    "&DireccionCliente=${editaDomicilio.text}&CorreoCliente=${editaCorreo.text}" +
                    "&TelefonoCliente=${editaTelefono.text}"

            val stringReq = StringRequest(
                Request.Method.GET, url,
                { response ->
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
                                    dialog, id ->
                                finish()

                            })

                        val alert = dialogBuilder.create()
                        alert.setTitle("Edicion de usuarios")
                        alert.show()

                    }
                },

                {
                    Alerta("Red","Error de conexión")

                })
            queue.add(stringReq)
        }

        edicionVolver.setOnClickListener { finish() }

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