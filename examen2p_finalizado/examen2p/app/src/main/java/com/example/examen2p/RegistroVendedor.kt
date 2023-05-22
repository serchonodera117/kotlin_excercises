package com.example.examen2p

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro_vendedor.*


class RegistroVendedor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_vendedor)


        val actionBar = supportActionBar
        actionBar?.hide()



        val municipios = arrayOf("Colima", "Villa de Alvarez")
        val adaptador = ArrayAdapter<String>(this, android.R.layout.simple_gallery_item,municipios)
        MunicipioVendedor.adapter = adaptador
        MunicipioNegocio.adapter = adaptador

        var pagoEfectivo = ""
        var pagoTarjeta = ""
        var pagoOxxo = ""
        var ofertaProducto = ""
        var ofertaServicio = ""

        Guardar.setOnClickListener {
            if(EfectivoSelect.isChecked){pagoEfectivo = "Pago en efectivo"}
            else{pagoEfectivo = "No aplica"}
            if(TarjetaSelect.isChecked){pagoTarjeta = "Pago con tarjeta"}
            else{pagoTarjeta = "No aplica"}
            if(OxxoSelect.isChecked){pagoOxxo = "Pago en oxxo"}
            else{pagoOxxo = "No aplica"}

            if(OfertaProducto.isChecked){ofertaProducto = "Venta de productos"}
            else{ofertaProducto = "No aplica"}
            if(OfertaServicio.isChecked){ofertaServicio = "Venta de servicios"}
            else{ofertaServicio = "No aplica"}

            val queue = Volley.newRequestQueue(this)
            val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_registro.php?" +
                    "NombreUsuario=${nombreVendedor.text.toString()}&ApellidosUsuario=${ApellidoVendedor.text.toString()}" +
                    "&ImagenUsuario=${ImagenVendedor.text.toString()}&MunicipioUsuario=${MunicipioVendedor.selectedItem.toString()}" +
                    "&ColoniaUsuario=${ColoniaVendedor.text.toString()}&DireccionUsuario=${DomicilioVendedor.text.toString()}" +
                    "&CorreoUsuario=${CorreoVendedor.text.toString()}&ContrasenaUsuario=${ContraseñaVendedor.text.toString()}" +
                    "&TipoUsuario=Vendedor&TelefonoUsuario=${TelefonoVendedor.text.toString()}&NombreNegocio=${NombreNegocio.text.toString()}" +
                    "&LogoUsuario=${urlNegocio.text.toString()}&Efectivo=${pagoEfectivo.toString()}&Tarjeta=${pagoTarjeta.toString()}" +
                    "&PagoOxxo=${pagoOxxo.toString()}&Oferta_Producto=${ofertaProducto}&Oferta_Servicio=${ofertaServicio.toString()}" +
                    "&CategoriaNegocio=${CategoriaNegocio.text.toString()}&MunicipioNegocio=${MunicipioNegocio.selectedItem.toString()}" +
                    "&ColoniaNegocio=${ColoniaNegocio.text.toString()}&DireccionNegocio=${DireccionNegocio.text.toString()}"

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
                        alert.setTitle("Alerta")
                        alert.show()
                    }
                },
                Response.ErrorListener {
                    Alerta("Red","Error de conexión")
                })
            queue.add(stringReq)
        }


        Volver.setOnClickListener{
            finish()
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
