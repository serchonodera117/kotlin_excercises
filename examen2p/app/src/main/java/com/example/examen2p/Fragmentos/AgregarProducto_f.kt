package com.example.examen2p.Fragmentos

import android.content.DialogInterface
import android.os.Bundle
import android.system.Os.close
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.R
import kotlinx.android.synthetic.main.activity_agregar_mi_negocio.*
import kotlinx.android.synthetic.main.fragment_agregar_producto_f.*


class AgregarProducto_f : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_producto_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    //------obtener nombre del negocio
        val negocioNombre = arguments?.getString("nombreNegocioAdmin")



    //------Registrar Productos
       GuardarProducto.setOnClickListener {
           var queue = Volley.newRequestQueue(activity)
           val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_registrarproductos.php?" +
                   "NombreNegocio=${negocioNombre}&NombreProducto=${nombreProducto.text}&ExistenciaProducto=${existenciaProducto.text.toString().toFloat()}" +
                   "&DescripcionProducto=${descripcionProducto.text}&Precio=${precioProducto.text}"

           val stringReq = StringRequest(
               Request.Method.GET, url,
               Response.Listener<String> { response ->
                   var strResp = response.toString()
                   if (strResp=="")
                   {
                       Alerta("productos","No se pudo registrar el producto!")

                   }

                   else
                   {     //mensaje de respuesta al registrar
                       val dialogBuilder = AlertDialog.Builder(requireActivity())
                       dialogBuilder.setMessage(strResp)
                           .setCancelable(false)
                           .setPositiveButton("OK", DialogInterface.OnClickListener {
                                   dialog, id ->
                               nombreProducto.setText("")
                               existenciaProducto.setText("")
                               descripcionProducto.setText("")
                               precioProducto.setText("")
                               dialog.cancel()
                           })
                       val alert = dialogBuilder.create()
                       alert.setTitle("Aviso")
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
        val dialogBuilder = AlertDialog.Builder(requireActivity())
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