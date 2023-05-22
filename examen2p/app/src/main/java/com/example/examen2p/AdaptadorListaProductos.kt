package com.example.examen2p

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.estilo_lista_productos.view.*

public class AdaptadorListaProductos(val productos: ArrayList<DatosProducto>):
                                    RecyclerView.Adapter<AdaptadorListaProductos.ViewHolder>(){

    private  lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.estilo_lista_productos, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun getItemCount(): Int = productos.size

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        return holder.render(productos.get(i))
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        fun render(miProducto: DatosProducto) {
            itemView.nombreMiproductoP.text = miProducto.NombreProducto
            itemView.descripciónMiproductoP.text = miProducto.DescripcionProducto
            itemView.existenciaMiproductoP.text = miProducto.ExistenciaProducto
            itemView.precioMiproductoP.text = miProducto.Precio

        }
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
    }
    }
}


