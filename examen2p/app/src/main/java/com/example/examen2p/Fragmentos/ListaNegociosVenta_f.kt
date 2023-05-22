package com.example.examen2p.Fragmentos

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_acciones_misventas.*
import kotlinx.android.synthetic.main.fragment_lista_negocios_venta_f.*
import kotlinx.android.synthetic.main.fragment_principal_f.*
import org.json.JSONArray
import org.json.JSONObject


class ListaNegociosVenta_f : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_negocios_venta_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = arguments?.getParcelable<DatosPersona>("usuarioDatos")

      //  pruebaVentaN.setText(usuario?.CorreoUsuario)

        ListaNegocios(usuario?.CorreoUsuario.toString(), usuario)
    }

    fun ListaNegocios(correo: String, usuario: DatosPersona?){
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarnegocios.php?" +
                "CorreoUsuario=${correo}"

        val respuestaString = StringRequest(
            Request.Method.GET, url, {
                    response ->
                //-----------------------------Obtención de un array json con los datos consultados
                var respuesta = response.toString()
                val jsonObj: JSONObject = JSONObject(respuesta)
                val jsonArray: JSONArray = jsonObj.getJSONArray("negocios")
                if(jsonArray.length()==0) {
                    Aviso("Usuarios", "no se encuentran negocios")
                }
                else{

                    //-----------------------------Almacenamiento del array en variable, y busqueda en atributos del objeto
                    var arrayNegocios = ArrayList<DatosNegocio>()
                    var arrayStNegocios = ArrayList<String>()
                    var index = 0

                    for(i in 0 until jsonArray.length())
                    {
                        var datos = JSONObject(jsonArray.getString(index).toString())
                        var d = DatosNegocio()

                        d.idnegocio = datos.getString("idnegocio").toString()
                        d.CorreoUsuario= datos.getString("CorreoUsuario").toString()
                        d.NombreNegocio = datos.getString("NombreNegocio").toString()
                        d.LogoUsuario = datos.getString("LogoUsuario").toString()
                        d.Efectivo = datos.getString("Efectivo").toString()
                        d.Tarjeta = datos.getString("Tarjeta").toString()
                        d.PagoOxxo = datos.getString("PagoOxxo").toString()

                        d.Oferta_Producto = datos.getString("Oferta_Producto").toString()
                        d.OfertaServicio = datos.getString("Oferta_Servicio").toString()
                        d.CategoriaNegocio = datos.getString("CategoriaNegocio").toString()
                        d.MunicipioNegocio = datos.getString("MunicipioNegocio").toString()
                        d.ColoniaNegocio = datos.getString("ColoniaNegocio").toString()
                        d.DireccionNegocio = datos.getString("DireccionNegocio").toString()

                        arrayNegocios.add(d)
                        arrayStNegocios.add(d.NombreNegocio)
                        index++
                    }

                    //--------Cargar adapter
                    listaVentasNegocioA.layoutManager = LinearLayoutManager(activity)
                    val adaptador = AdaptadorListaNegocios(arrayNegocios)
                    listaVentasNegocioA.adapter = adaptador


                    //--------Asignación del metodo setOnItemClickListener
                    adaptador.setOnItemClickListener(object : AdaptadorListaNegocios.onItemClickListener{
                        override fun onItemClick(position: Int) {

                    //------cambio a los productos
                            val fragment = ListaVentasProductos_F()
                            val transaccion = fragmentManager?.beginTransaction()
                            transaccion?.replace(R.id.listaVentaM, fragment)
                           val bunle = Bundle()
                              bunle.putParcelable("NegocioDatos", arrayNegocios.get(position))
                            fragment.arguments = bunle
                            transaccion?.commit()
                        }
                    })
                }
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