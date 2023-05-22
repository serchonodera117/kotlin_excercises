package com.example.examen2p

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.examen2p.Fragmentos.AgregarProducto_f
import com.example.examen2p.Fragmentos.EditarProducto_f
import com.example.examen2p.Fragmentos.ListaProductos_f
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_negocios_administrar.*

class NegociosAdministrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_negocios_administrar)

        var barraSuperior = supportActionBar
           barraSuperior?.hide()


    //----Fregmentos
        var listaProductos = ListaProductos_f()
        var pantallaAgregarP = AgregarProducto_f()



        //-----lista de productos
        reemplazarFragmentos(listaProductos)

    //-----obtener datos del negocio
        val bundle = intent.extras
        var nombreNegocio = bundle?.getString("nombreNegocio")
        var imagenNegocio = bundle?.getString("imagenNegocio")
        var categoriaNegocio = bundle?.getString("categoriaNegocio")

        nombreMiNegocio.setText(nombreNegocio)
        Picasso.get().load(imagenNegocio).into(logoMiNegocio)
        categoriaMiNegocio.setText(categoriaNegocio)



        //-----Pasar el nombre del negocio (llave para los productos) al fragment Lista
        var productosLlave = Bundle()
        productosLlave.putString("nombreNegocioLista", nombreNegocio)
        listaProductos.arguments = productosLlave



    //----fragment para añadir productos
        agregarProducto.setOnClickListener {
            var negocioNombre = Bundle()
                negocioNombre.putString("nombreNegocioAdmin", nombreMiNegocio.text.toString())
                pantallaAgregarP.arguments = negocioNombre

            reemplazarFragmentos(pantallaAgregarP)
            misProductosTitle.visibility = View.VISIBLE
        }

        //---recibirActivacionEdicion
        var bunle = intent.extras
        var activacionEdicion = bunle?.getString("activacion")


        volverLista.setOnClickListener {
            reemplazarFragmentos(listaProductos)
            activacionEdicion = "Desactivado"
            misProductosTitle.visibility = View.GONE
        }

        volverPrincipal.setOnClickListener { finish() }
    }


//-----Función para cambiar de fragmentos en el menú navegable
    private fun reemplazarFragmentos(fragment: Fragment){
        if(fragment != null){
            val transaccion = supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.contenedorListaProductos, fragment)
            transaccion.commit()
        }
    }
}