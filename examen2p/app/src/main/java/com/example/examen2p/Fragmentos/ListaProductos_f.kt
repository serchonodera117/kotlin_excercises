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
import kotlinx.android.synthetic.main.activity_negocios_administrar.*
import kotlinx.android.synthetic.main.activity_registro_vendedor.*
import kotlinx.android.synthetic.main.fragment_lista_productos_f.*
import kotlinx.android.synthetic.main.fragment_mis_clientes_f.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ListaProductos_f : Fragment() {
    var lista_de_productos = ArrayList<DatosProducto>()
    var productosBuscados = ArrayList<DatosProducto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_productos_f, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        limpiarBusquedaProductos.visibility = View.GONE

        //---obtener nombre de negocio
        val negocioNombre = arguments?.getString("nombreNegocioLista")

        lista_de_productos.clear()
        ListarProductos(negocioNombre.toString())

        botonBuscarProducto.setOnClickListener{
            productosBuscados.clear()
            limpiarBusquedaProductos.visibility = View.VISIBLE

            if(cajaBusquedaProductos.text.isEmpty()){
                Toast.makeText(requireActivity(), "Buscador vacío", Toast.LENGTH_SHORT).show()
            }else{
                for(a in lista_de_productos){
                    if(cajaBusquedaProductos.text.toString() contentEquals a.NombreProducto){
                        productosBuscados.add(a)
                    }
                }
                if(!productosBuscados.isEmpty()){
                    listaMisProductosN.clearFocus()
                    listaMisProductosN.layoutManager = LinearLayoutManager(activity)
                    val adaptador = AdaptadorListaProductos(productosBuscados)
                    listaMisProductosN.adapter = adaptador

                    adaptador.setOnItemClickListener(object : AdaptadorListaProductos.onItemClickListener{
                        override fun onItemClick(position: Int){
                            Acciones(productosBuscados, position)
                        }})

                }else{
                    Toast.makeText(requireActivity(), "${cajaBusquedaProductos.text} no se encontró", Toast.LENGTH_SHORT).show()
                }
            }
        }

        limpiarBusquedaProductos.setOnClickListener{
            cajaBusquedaProductos.setText("")
            limpiarBusquedaProductos.visibility = View.GONE
            lista_de_productos.clear()
            ListarProductos(negocioNombre.toString())
        }

    }

    fun ListarProductos(negocio: String){
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarproductos.php?" +
                "NombreNegocio=${negocio}"

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
                        listaMisProductosN.layoutManager = LinearLayoutManager(activity)
                        val miadaptador = AdaptadorListaProductos(lista_de_productos)
                        listaMisProductosN.adapter = miadaptador

                        //---asignacion del metodo clic listener
                        miadaptador.setOnItemClickListener(object :
                            AdaptadorListaProductos.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                Acciones(lista_de_productos, position)
                            }
                        })

                    }
                }else{Aviso("Aviso", "no cuenta con productos registrados")}
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

    fun Acciones(listaProductos: ArrayList<DatosProducto>, position: Int){
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("Oprima uno de los botones, o fuera del recuadro para cancelar")
            .setCancelable(true)
            .setPositiveButton(
                "Editar",
                DialogInterface.OnClickListener {                    //abrir ediciom
                        dialog, id ->
                    var edicionProductos = EditarProducto_f()

                    var bundle = Bundle()
                    bundle.putParcelable("datosEdicion", listaProductos.get(position))        //enviar a pantalla de elominación
                    edicionProductos.arguments = bundle

                    val transaccion = fragmentManager?.beginTransaction()
                    transaccion?.replace(R.id.contenedorListaProductos, edicionProductos)
                    transaccion?.commit()

                    dialog.cancel()
                }).setNegativeButton("Eliminar", DialogInterface.OnClickListener {          //enviar a pantalla de eliminación
                    dialog, id ->
                val eliminar = EliminacionProducto_f()

                var bunle = Bundle()
                bunle.putParcelable("datosEliminacion", listaProductos.get(position))
                eliminar.arguments = bunle

                val transicion = fragmentManager?.beginTransaction()
                transicion?.replace(R.id.contenedorListaProductos, eliminar)
                transicion?.commit()

                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Acciones")
        alert.show()

    }
}