package com.example.examen2p.Fragmentos

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.examen2p.*
import kotlinx.android.synthetic.main.fragment_lista_productos_f.*
import kotlinx.android.synthetic.main.fragment_mis_clientes_f.*
import org.json.JSONArray
import org.json.JSONObject


 class MisClientes_f : Fragment() {
    var arraydeClientes = arrayListOf<DatosCliente>()
    var arrayBusquedaClientes = arrayListOf<DatosCliente>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_clientes_f, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  indicadorListaClientes.visibility = View.GONE

   //obtención de los datos del usuario de principal activity
        val correo = arguments?.getParcelable<DatosPersona>("correoVendedor")

        borrarBusquedaCliente.visibility = View.GONE


    //--- abrir el activity agregar
        agregarCliente.setOnClickListener {
            val a = Intent(requireActivity(), RegistroCliente::class.java)
            a.putExtra("correoLlave", correo?.CorreoUsuario)
             startActivity(a)
        }
        arraydeClientes.clear()
        listarMisClientes(correo?.CorreoUsuario.toString())

        //----buscador
        botonBuscarCliente.setOnClickListener {
            arrayBusquedaClientes.clear()
            borrarBusquedaCliente.visibility = View.VISIBLE

            if (!cajaBusquedaClientes.text.isEmpty()) {
                for (a in arraydeClientes) {
                    if (a.NombreCliente.toString() contentEquals cajaBusquedaClientes.text) {
                        arrayBusquedaClientes.add(a)
                    }
                }

                if (!arrayBusquedaClientes.isEmpty()) {
                    clientesMiLista.clearFocus()
                    clientesMiLista.layoutManager = LinearLayoutManager(activity)
                    val adaptador = AdaptadorListaClientes(arrayBusquedaClientes)
                    clientesMiLista.adapter = adaptador
                    adaptador.setOnItemClickListener(object : AdaptadorListaClientes.onItemClickListener{
                        override fun onItemClick(position: Int){
                            Acciones(arrayBusquedaClientes, position, correo?.CorreoUsuario.toString())
                        }})


                } else Toast.makeText(requireActivity(), "No existe ${cajaBusquedaClientes.text} en los registros",
                    Toast.LENGTH_SHORT).show()
            }else Toast.makeText(requireActivity(), "Buscador vacío", Toast.LENGTH_SHORT).show()
        }

        borrarBusquedaCliente.setOnClickListener {
            clientesMiLista.clearFocus()
            arraydeClientes.clear()
            borrarBusquedaCliente.visibility = View.GONE
            cajaBusquedaClientes.setText("")
            listarMisClientes(correo?.CorreoUsuario.toString())
        }

        RefrescarClientes.setOnClickListener {
            arraydeClientes.clear()
            listarMisClientes(correo?.CorreoUsuario.toString())
        }
    }

    fun listarMisClientes (correo: String)
    {
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarclientes.php?" +
                "correoVendedor=${correo}"

        val respuestaString = StringRequest(
            Request.Method.GET, url, { response ->
                //-----------------------------Obtención de un array json con los datos consultados
                Log.i("response",response.toString())

                if(response.toString() != "0 resultados"){

                    var respuesta = response.toString()
                    val jsonObj: JSONObject = JSONObject(respuesta)
                    val jsonArray: JSONArray = jsonObj.getJSONArray("clientes")
                    if (jsonArray.length() == 0) {
                        Aviso("Usuarios", "no se encuentran clientes")
                    } else {


                        var nombresClientes = arrayListOf<String>()
                        var indice = 0
                        //-----------------------------Almacenamiento del array en variable, y busqueda en atributos del objeto
                        for(i in 0 until jsonArray.length()){
                            var a = DatosCliente()
                            var datos = JSONObject(jsonArray.getString(indice).toString())

                            a.idcliente = datos.getString("idcliente").toString()
                            a.correoVendedor = datos.getString("correoVendedor").toString()
                            a.NombreCliente= datos.getString("NombreCliente").toString()
                            a.ApellidosCliente = datos.getString("ApellidosCliente").toString()
                            a.ImagenCliente = datos.getString("ImagenCliente").toString()
                            a.MunicipioClientee = datos.getString("MunicipioCliente").toString()
                            a.DireccionCliente= datos.getString("DireccionCliente").toString()
                            a.CorreoCliente = datos.getString("CorreoCliente").toString()
                            a.TelefonoCliente = datos.getString("TelefonoCliente").toString()
                            a.TipoUsuario = datos.getString("TipoUsuario").toString()



                            arraydeClientes.add(a)
                            indice++
                        }


                        //cargar adapter
                          clientesMiLista.layoutManager = LinearLayoutManager(activity)
                        val adaptador = AdaptadorListaClientes(arraydeClientes)
                        clientesMiLista.adapter = adaptador

                        //asignación del clicListener
                        adaptador.setOnItemClickListener(object : AdaptadorListaClientes.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                Acciones(arraydeClientes, position, correo)
                            }
                        })
                    }
                }else{Aviso("Aviso", "no cuenta con clientes registrados")}
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

    fun Borrar(idcliente: String, correo: String){ //Eliminar cliente
        val queue = Volley.newRequestQueue(activity)
        val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_eliminarclientes.php?" +
                "idcliente=${idcliente.toInt()}"

        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var strResp = response.toString()
                if (strResp=="")
                {
                    Aviso("Usuarios","No se pudo registrar!")

                }

                else
                {     //mensaje de respuesta al registrar

                    val dialogBuilder = AlertDialog.Builder(requireActivity())
                    dialogBuilder.setMessage(strResp)
                        .setCancelable(false)
                        .setPositiveButton("OK", DialogInterface.OnClickListener {

                                dialog, id -> dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Aviso ")
                    alert.show()

                }
            },

            {
                Aviso("Red","Error de conexión")

            })
        arraydeClientes.clear()
        listarMisClientes(correo)
        queue.add(stringReq)
      }

    fun Acciones(arrayClientes: ArrayList<DatosCliente>, position: Int, correo: String){
        val dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setMessage("Seleccione una de las acciones, o cliquee afuera para cerrar")
            .setCancelable(true)
            .setPositiveButton("Editar", DialogInterface.OnClickListener { //editar usuario... abre esa pantalla
                    dialog, id ->
                val editar = Intent(requireActivity(), EditarCliente::class.java)
                editar.putExtra("datosCliente", arrayClientes.get(position))
                startActivity(editar)
            })
            .setNegativeButton("Eliminar", DialogInterface.OnClickListener{ //eliminar usuario, abre otro modal para preguntar
                    dialog, id ->

                //-----modal para borrar
                val dialogBuilder = AlertDialog.Builder(requireActivity())
                dialogBuilder.setMessage("Está por eliminar a ${arrayClientes.get(position).NombreCliente}. ¿Está seguro?")
                    .setCancelable(false)
                    .setPositiveButton("Borrar", DialogInterface.OnClickListener {
                            dialog, id ->
                        Borrar(arrayClientes.get(position).idcliente, correo)
                        dialog.cancel()
                    }).setNegativeButton("Cancelar", DialogInterface.OnClickListener{
                            dialog, id -> dialog.cancel()
                    })
                val alert = dialogBuilder.create()
                alert.setTitle("Advertencia")
                alert.show()


            })
        val alert = dialogBuilder.create()
        alert.setTitle("Acciones")
        alert.show()
    }



}


