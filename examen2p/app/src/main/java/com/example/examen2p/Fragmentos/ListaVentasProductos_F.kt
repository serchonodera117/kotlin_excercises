package com.example.examen2p.Fragmentos

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.*
import kotlinx.android.synthetic.main.fragment_agregar_producto_f.*
import kotlinx.android.synthetic.main.fragment_lista_productos_f.*
import kotlinx.android.synthetic.main.fragment_lista_ventas_productos_.*
import org.json.JSONArray
import org.json.JSONObject


class ListaVentasProductos_F : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_ventas_productos_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //datos del negocio
             var negocio = arguments?.getParcelable<DatosNegocio>("NegocioDatos")

       ListarProductos(negocio)

    }

    fun ListarProductos(negocio: DatosNegocio?){
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarproductos.php?" +
                "NombreNegocio=${negocio?.NombreNegocio}"

        val respuestaString = StringRequest(
            Request.Method.GET, url, { response ->
                //-----------------------------Obtención de un array json con los datos consultados
                Log.i("response",response.toString())

                if(response.toString() != "0 resultados"){

                    var respuesta = response.toString()
                    val jsonObj: JSONObject = JSONObject(respuesta)
                    val jsonArray: JSONArray = jsonObj.getJSONArray("productos")
                    if (jsonArray.length() == 0) {
                        Aviso("Usuarios", "no se encuentran negocios")
                    } else {
                       var lista_de_productos = ArrayList<DatosProducto>()
                        var indice = 0

                        //-----------------------------Almacenamiento del array en variable, y busqueda en atributos del objeto
                        for (i in 0 until jsonArray.length()) {
                            var producto = DatosProducto()
                            var datos = JSONObject(jsonArray.get(indice).toString())

                            producto.idproducto = datos.getString("idproducto").toString()
                            producto.NombreNegocio = datos.getString("NombreNegocio").toString()
                            producto.NombreProducto = datos.getString("NombreProducto").toString()
                            producto.DescripcionProducto =
                                datos.getString("DescripcionProducto").toString()
                            producto.ExistenciaProducto =
                                datos.getString("ExistenciaProducto").toString()
                            producto.Precio = datos.getString("Precio").toString()

                            lista_de_productos.add(producto)
                            indice++
                        }

                        //---cargar adaptador
                        productosParaVenta.layoutManager = LinearLayoutManager(activity)
                        val miadaptador = AdaptadorListaProductos(lista_de_productos)
                         productosParaVenta.adapter = miadaptador

                        //---asignacion del metodo clic listener
                        miadaptador.setOnItemClickListener(object :
                            AdaptadorListaProductos.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                //Toast.makeText(requireActivity(), "funciona ${lista_de_productos.get(position).NombreProducto}", Toast.LENGTH_SHORT).show()

                        //----llenar el objeto venta con los datos necesarios de VENDEDOR, PRODUCTO y proximamente CLIENTE
                                var venta = DatosVenta()

                                venta.correoVendedor = negocio?.CorreoUsuario.toString()
                                venta.idnegocio = negocio?.idnegocio.toString()
                                venta.NombreNegocio = negocio?.NombreNegocio.toString()
                                venta.idproducto = lista_de_productos.get(position).idproducto
                                venta.NombreProducto = lista_de_productos.get(position).NombreProducto
                                venta.DescripcionProducto = lista_de_productos.get(position).DescripcionProducto
                                venta.PrecioProducto = lista_de_productos.get(position).Precio

                                venta.Existencia = lista_de_productos.get(position).ExistenciaProducto

                                var clientesVentas = Clientes_Venta_f()

                             //--- enviar objeto venta a  clientes_venta
                                var bundle = Bundle()
                                 bundle.putParcelable("miVenta", venta)
                                 clientesVentas.arguments = bundle

                                val transaccion = fragmentManager?.beginTransaction()
                                transaccion?.replace(R.id.listaVentaM, clientesVentas)
                                transaccion?.commit()



                            }
                        })

                    }
                }else{Aviso("Aviso", "${negocio?.NombreNegocio} no cuenta con productos registrados")}
            },
            {
                Aviso("Red", "No se pudo conectar a la base")
            })
        queue.add(respuestaString)
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