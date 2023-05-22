package com.example.examen2p.Fragmentos

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.DatosVenta
import com.example.examen2p.R
import kotlinx.android.synthetic.main.fragment_agregar_producto_f.*
import kotlinx.android.synthetic.main.fragment_formulario_venta_f.*
import kotlinx.android.synthetic.main.fragment_lista_ventas_productos_.*

class FormularioVenta_f : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_venta_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var miVenta = arguments?.getParcelable<DatosVenta>("ventaFormulario")

        nameClienteVenta.setText("${miVenta?.NombreCliente} ${miVenta?.ApellidosCliente}")
        clienteIdVenta.setText("Id Cliente: ${miVenta?.idCliente}")
        nombreNegocioVender.setText(miVenta?.NombreNegocio)

        idProductoYNombreP.setText("Id: 000${miVenta?.idproducto} Producto: ${miVenta?.NombreProducto}")
        descripcionProductoVender.setText("Descripción: \n ${miVenta?.DescripcionProducto}")

        precioProductoVender.setText("${miVenta?.PrecioProducto} $")
        existenciaProductoVender.setText("En stonk: ${miVenta?.Existencia}")

       var cantidadCompras:Int = 0
        var precio = miVenta?.PrecioProducto.toString().toFloat()
        var precioFinal:Float = 0f
        var existencias = miVenta?.Existencia.toString().toInt()
        var residuo:Int = 0

        CantidadAVender.setText(cantidadCompras.toString())


        incremento.setOnClickListener {
            cantidadCompras = CantidadAVender.text.toString().toInt()
            PrecioFinalVenta.setText("Precio final: ${precioFinal.toString()} $")
            cantidadCompras += 1
            CantidadAVender.setText(cantidadCompras.toString())
            precioFinal = precio * cantidadCompras
            PrecioFinalVenta.setText("Precio final: ${precioFinal.toString()} $")
        }

        decremento.setOnClickListener{
            cantidadCompras = CantidadAVender.text.toString().toInt()
           if(cantidadCompras > 0)
                cantidadCompras -= 1
                CantidadAVender.setText(cantidadCompras.toString())
                precioFinal = precio * cantidadCompras
              PrecioFinalVenta.setText("Precio final: ${precioFinal.toString()} $")
       }

        //---escuchar a tiempo real
        cometerVenta.setOnClickListener{
              cantidadCompras = CantidadAVender.text.toString().toInt()
            if(cantidadCompras < 1 )  Aviso("Aviso", "No ha definido una cantiad valida de productos")
            else if(cantidadCompras > existencias){Aviso("Aviso", "la antidad solicitada supera las existencias")}
            else{
               residuo = existencias -  CantidadAVender.text.toString().toInt()
                precioFinal = precio * CantidadAVender.text.toString().toFloat()
                PrecioFinalVenta.setText("Precio final: ${precioFinal.toString()} $")

                GuardarVenta(miVenta?.correoVendedor.toString(), miVenta?.idCliente.toString(),miVenta?.NombreCliente.toString(), miVenta?.ApellidosCliente.toString(),
                       miVenta?.idnegocio.toString(), miVenta?.NombreNegocio.toString(), miVenta?.idproducto.toString(), miVenta?.NombreProducto.toString(), miVenta?.DescripcionProducto.toString(),
                     miVenta?.PrecioProducto.toString(), cantidadCompras.toString(), precioFinal.toString(), residuo.toString())
            }
        }
    }

    fun GuardarVenta(correoVendedor:String, idCliente:String, NombreCliente:String, ApellidosCliente:String,
                   idnegocio:String, NombreNegocio:String, idproducto:String, NombreProducto:String,
                 DescripcionProducto:String, PrecioProducto:String, CantidadProducto:String, PrecioFinal:String, ExistenciaProducto:String)
    {
        var queue = Volley.newRequestQueue(activity)

        var url = "https://registrosappinventor.000webhostapp.com/examen2p_registrarventa.php?correoVendedor=${correoVendedor}"+
                "&idCliente=${idCliente.toInt()}&NombreCliente=${NombreCliente}&ApellidosCliente=${ApellidosCliente}&idnegocio=${idnegocio.toInt()}" +
                "&NombreNegocio=${NombreNegocio}&idproducto=${idproducto.toInt()}&NombreProducto=${NombreProducto}&DescripcionProducto=${DescripcionProducto}" +
                "&PrecioProducto=${PrecioProducto.toFloat()}&CantidadProducto=${CantidadProducto.toInt()}&PrecioFinal=${PrecioFinal.toFloat()}&ExistenciaProducto=${ExistenciaProducto.toInt()}"

        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var strResp = response.toString()
                if (strResp == "") {
                    Aviso("productos", "No se pudo registrar el producto!")

                } else {     //mensaje de respuesta al registrar
                    val dialogBuilder = AlertDialog.Builder(requireActivity())
                    dialogBuilder.setMessage(strResp)
                        .setCancelable(false)
                        .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                            val ButasVentas = activity
                            ButasVentas?.finish()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Aviso")
                    alert.show()

                }
            },
            {
                Aviso("Red", "Error de conexión")
            })
        queue.add(stringReq)
    }

    fun Aviso(Titulo:String, Mensaje:String)
    {
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