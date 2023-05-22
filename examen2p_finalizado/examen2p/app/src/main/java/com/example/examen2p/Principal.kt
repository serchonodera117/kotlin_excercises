package com.example.examen2p

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.examen2p.Fragmentos.MisClientes_f
import com.example.examen2p.Fragmentos.MisVentas_f
import com.example.examen2p.Fragmentos.Principal_f
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.fragment_principal_f.*

class Principal : AppCompatActivity() {
    private val principalPagina = Principal_f()
    private val todoslosClientes = MisClientes_f()
    private val tadaslasVentas = MisVentas_f()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        var barraSuperior = supportActionBar
        barraSuperior?.hide()

//-----obtención de datos del usuario
           var bunle = intent.extras
            var datosUsuario = bunle?.getParcelable<DatosPersona>("datosUsuario")
                    //(leer fotos por url)    Picasso.get().load(datosUsuario?.ImagenUsuario).into(fotoPerfl)


//-----Enviar al info al fragment principal y mis ventas
        var fragmentUserDato = Bundle()
            fragmentUserDato.putParcelable("datosUsuario", datosUsuario)
            principalPagina.arguments = fragmentUserDato
            tadaslasVentas.arguments = fragmentUserDato

//----Enviar la llave (correo) al fragmento principal
        var clienteNegocio = Bundle()
        clienteNegocio.putParcelable("correoVendedor", datosUsuario)
        todoslosClientes.arguments = clienteNegocio

//-----Cambios del menu navegable
        reemplazarFragmentos(principalPagina)
        menuNavegable.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.misNegocios ->reemplazarFragmentos(principalPagina)
                R.id.misClientes -> reemplazarFragmentos(todoslosClientes)
                R.id.misVentas -> reemplazarFragmentos(tadaslasVentas)
            }
            true
        }
        var listaNegocios = ArrayList<DatosNegocio>()
        var correo = datosUsuario?.CorreoUsuario

         var lista = Bundle()
            lista.putParcelableArrayList("listaNegocios",listaNegocios)



    }

//-----Función para cambiar de fragmentos en el menú navegable
       private fun reemplazarFragmentos(fragment: Fragment){
        if(fragment != null){
            val transaccion = supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.contenedor, fragment)
            transaccion.commit()
        }
    }

//----Funcion para llenar la lista de datos

}





