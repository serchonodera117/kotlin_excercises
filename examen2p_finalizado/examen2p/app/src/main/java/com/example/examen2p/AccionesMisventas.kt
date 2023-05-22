package com.example.examen2p

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.examen2p.Fragmentos.ListaNegociosVenta_f
import kotlinx.android.synthetic.main.activity_acciones_misventas.*

class AccionesMisventas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //----declaración de fragments
        var lista_de_negocios = ListaNegociosVenta_f()

        var barraSuperior = supportActionBar
           barraSuperior?.hide()

        //----visibilidad de botones
        setContentView(R.layout.activity_acciones_misventas)


        //--- recibir datos del usuario
        var bunle = intent.extras
        var usuario =  bunle?.getParcelable<DatosPersona>("usuarioDatos")

    //----- Inicialización del fragment
        var framgentNegociosLista = Bundle()
            framgentNegociosLista.putParcelable("usuarioDatos", usuario)
            lista_de_negocios.arguments = framgentNegociosLista

        reemplazo(lista_de_negocios)




        cerrarAccionesVentas.setOnClickListener { finish() }

        listaNegociosVenta.setOnClickListener{
           reemplazo(lista_de_negocios)
        }

    }

    fun reemplazo(fragment: Fragment){
        if(fragment !=null){
            val transaccion = supportFragmentManager.beginTransaction()
            transaccion.replace(R.id.listaVentaM, fragment)
            transaccion.commit()
        }
    }
}