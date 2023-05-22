package com.example.examen2p.Fragmentos

import android.content.DialogInterface
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_lista_productos_f.*
import kotlinx.android.synthetic.main.fragment_mis_clientes_f.*
import kotlinx.android.synthetic.main.fragment_mis_ventas_f.*
import org.json.JSONArray
import org.json.JSONObject

class MisVentas_f : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_ventas_f, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datosUsuario = arguments?.getParcelable<DatosPersona>("datosUsuario")

        nuevaVenta.setOnClickListener{
            val ventas = Intent(requireActivity(), AccionesMisventas::class.java)
            ventas.putExtra("usuarioDatos", datosUsuario)
            startActivity(ventas)
        }

        ListarProductos(datosUsuario?.CorreoUsuario.toString())

        RegrescarVentas.setOnClickListener {
            ListarProductos(datosUsuario?.CorreoUsuario.toString())
        }
    }

    fun ListarProductos(correo: String){
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarventas.php?" +
                "CorreoUsuario=${correo}"

        val respuestaString = StringRequest(
            Request.Method.GET, url, { response ->
                //-----------------------------Obtención de un array json con los datos consultados
                Log.i("response",response.toString())

                if(response.toString() != "0 resultados"){

                    var respuesta = response.toString()
                    val jsonObj: JSONObject = JSONObject(respuesta)
                    val jsonArray: JSONArray = jsonObj.getJSONArray("ventas")
                    if (jsonArray.length() == 0) {
                        Aviso("Usuarios", "no se encuentran negocios")
                    } else {
                        var  arrayVentas = ArrayList<DatosVenta>()
                        var indice = 0

                        //-----------------------------Almacenamiento del array en variable, y busqueda en atributos del objeto
                        for (i in 0 until jsonArray.length()) {
                            var venta = DatosVenta()
                            var datos = JSONObject(jsonArray.get(indice).toString())

                            venta.NombreNegocio = datos.getString("NombreNegocio").toString()
                            venta.NombreCliente = datos.getString("NombreCliente").toString()
                            venta.ApellidosCliente = datos.getString("ApellidosCliente").toString()
                            venta.idCliente = datos.getString("idCliente").toString()
                            venta.NombreProducto = datos.getString("NombreProducto").toString()
                            venta.DescripcionProducto = datos.getString("DescripcionProducto").toString()
                            venta.CantidadProducto = datos.getString("CantidadProducto").toString()
                            venta.PrecioProducto = datos.getString("PrecioProducto").toString()
                            venta.PrecioFinal = datos.getString("PrecioFinal").toString()

                            arrayVentas.add(venta)

                            indice++
                        }



                        //---cargar adaptador
                       misVentasLista.layoutManager = LinearLayoutManager(activity)
                        val miadaptador = AdaptadorListaVentas(arrayVentas)
                        misVentasLista.adapter = miadaptador

                        //---asignacion del metodo clic listener
                        miadaptador.setOnItemClickListener(object :
                            AdaptadorListaVentas.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                Toast.makeText(requireActivity(), "${arrayVentas.get(position).NombreNegocio}: venta de ${arrayVentas.get(position).NombreProducto}",
                                Toast.LENGTH_SHORT).show()
                            }
                        })

                    }
                }else{Aviso("Aviso", "no cuenta con ventas registradas")}
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