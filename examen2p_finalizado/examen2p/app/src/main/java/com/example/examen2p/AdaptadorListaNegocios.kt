package com.example.examen2p

import android.app.Activity
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.estilo_listanegocios.view.*
import java.text.FieldPosition

public class AdaptadorListaNegocios(val negocios: ArrayList<DatosNegocio>):
                           RecyclerView.Adapter<AdaptadorListaNegocios.ViewHolder>() {

    private  lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.estilo_listanegocios, parent, false)
        return ViewHolder(v,mListener)
    }

    override fun getItemCount(): Int = negocios.size

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        return holder.render(negocios.get(i))
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        fun render(miNegocio: DatosNegocio) {
            itemView.negocioNombre.text = miNegocio.NombreNegocio
            itemView.negocioCategoria.text = miNegocio.CategoriaNegocio
            itemView.negocioMunicipio.text = miNegocio.MunicipioNegocio + " col"
            Picasso.get().load(miNegocio.LogoUsuario).into(itemView.negocioImagen)

        }

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}