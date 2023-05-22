package com.example.examen2p

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.Fragmentos.Principal_f
import kotlinx.android.synthetic.main.activity_agregar_mi_negocio.*
import kotlinx.android.synthetic.main.fragment_principal_f.*

class AgregarMiNegocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_mi_negocio)
        var barra = supportActionBar
           barra?.hide()

        //------obtención de datos
        var bundle = intent.extras
        var usuario = bundle?.getString("correoUsuario")

//------Adaptadores para los spiners
        var municipios = arrayOf("Colima","Villa de Alvarez")
        var adaptador = ArrayAdapter<String>(this, android.R.layout.simple_gallery_item, municipios)
        nuevoMunicipioNegocio.adapter = adaptador

//-----variables para chechbox
        var pagoEfectivo = ""
        var pagoTarjeta = ""
        var pagoOxxo = ""
        var ofertaProducto = ""
        var ofertaServicio = ""

        volverInicio.setOnClickListener { finish() }

//------Guardar nuevo Negocio
        GuardarNuevoNegocio.setOnClickListener {
            if(nuevoEfectivoSelect.isChecked){pagoEfectivo = "Pago en efectivo"}
            else{pagoEfectivo = "No aplica"}
            if(nuevoTarjetaSelect.isChecked){pagoTarjeta = "Pago con tarjeta"}
            else{pagoTarjeta = "No aplica"}
            if(nuevoOxxoSelect.isChecked){pagoOxxo = "Pago en oxxo"}
            else{pagoOxxo = "No aplica"}

            if(nuevoOfertaProducto.isChecked){ofertaProducto = "Venta de productos"}
            else{ofertaProducto = "No aplica"}
            if(nuevoOfertaServicio.isChecked){ofertaServicio = "Venta de servicios"}
            else{ofertaServicio = "No aplica"}


            var queue = Volley.newRequestQueue(this)
            val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_registrarnegocios.php?CorreoUsuario=${usuario}" +
                    "&NombreNegocio=${nuevoNombreNegocio.text.toString()}&LogoUsuario=${nuevoUrlNegocio.text.toString()}&Efectivo=${pagoEfectivo}" +
                    "&Tarjeta=${pagoTarjeta}&PagoOxxo=${pagoOxxo}&Oferta_Producto=${ofertaProducto}&Oferta_Servicio=${ofertaServicio}" +
                    "&CategoriaNegocio=${nuevoCategoriaNegocio.text.toString()}&MunicipioNegocio=${nuevoMunicipioNegocio.selectedItem.toString()}" +
                    "&ColoniaNegocio=${nuevoColoniaNegocio.text.toString()}&DireccionNegocio=${nuevoDireccionNegocio.text.toString()}"

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

                        Alerta("Alerta", strResp)

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setMessage(strResp)
                            .setCancelable(false)
                            .setPositiveButton("OK", DialogInterface.OnClickListener {
                                    dialog, id ->
                                finish()

                            })
                        val alert = dialogBuilder.create()
                        alert.setTitle("Aviso")
                        alert.show()

                    }
                },
                {
                    Alerta("Red","Error de conexión")
                })
            queue.add(stringReq)
        }

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