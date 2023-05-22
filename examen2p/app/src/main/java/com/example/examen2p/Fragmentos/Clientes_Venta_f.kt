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
import com.example.examen2p.AdaptadorListaClientes
import com.example.examen2p.DatosCliente
import com.example.examen2p.DatosVenta
import com.example.examen2p.R
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_clientes__venta_f.*
import kotlinx.android.synthetic.main.fragment_mis_clientes_f.*
import org.json.JSONArray
import org.json.JSONObject

class Clientes_Venta_f : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes__venta_f, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    //---- obtener la venta y sus datos

        var miVenta = arguments?.getParcelable<DatosVenta>("miVenta")

        ListarLosClientes(miVenta)
    }

    fun ListarLosClientes(miVenta: DatosVenta?){
            val queue = Volley.newRequestQueue(activity)
            val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_listarclientes.php?" +
                    "correoVendedor=${miVenta?.correoVendedor}"

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

                            var arraydeClientes = arrayListOf<DatosCliente>()
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
                            clientesParaVenta.layoutManager = LinearLayoutManager(activity)
                            val adaptador = AdaptadorListaClientes(arraydeClientes)
                            clientesParaVenta.adapter = adaptador

                            //asignación del clicListener
                            adaptador.setOnItemClickListener(object : AdaptadorListaClientes.onItemClickListener{
                                override fun onItemClick(position: Int) {
                                    //Toast.makeText(requireActivity(), "funciona :'D", Toast.LENGTH_SHORT).show()
                                    //quedan los datos del cliente. . .
                                   miVenta?.idCliente = arraydeClientes.get(position).idcliente
                                    miVenta?.NombreCliente = arraydeClientes.get(position).NombreCliente
                                   miVenta?.ApellidosCliente = arraydeClientes.get(position).ApellidosCliente

                                    val fragment = FormularioVenta_f()

                                    var bunle = Bundle()
                                    bunle.putParcelable("ventaFormulario", miVenta)
                                    fragment.arguments = bunle

                                    val transaccion = fragmentManager?.beginTransaction()
                                    transaccion?.replace(R.id.listaVentaM, fragment)
                                    transaccion?.commit()
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
}