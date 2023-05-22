package com.example.examen2p

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.estilo_lista_productos.view.*
import kotlinx.android.synthetic.main.estilo_lista_ventas.view.*

public class AdaptadorListaVentas(val ventas: ArrayList<DatosVenta>):
            RecyclerView.Adapter<AdaptadorListaVentas.ViewHolder>(){
    private  lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.estilo_lista_ventas, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun getItemCount(): Int = ventas.size

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        return holder.render(ventas.get(i))
    }


    inner class ViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView) {
        fun render(miVenta: DatosVenta) {
            itemView.ventaListNombreNegocio.text = miVenta.NombreNegocio
            itemView.ventaListNombreCompleto.text = "${miVenta.NombreCliente} ${miVenta.ApellidosCliente}  ID: 000${miVenta.idCliente}"
            itemView.ventaListProducto.text = miVenta.NombreProducto
            itemView.ventaListPrecio.text = "${miVenta.PrecioProducto} $"
            itemView.ventaListDescripcion.text = miVenta.DescripcionProducto
            itemView.ventaListCantidad.text = miVenta.CantidadProducto
            itemView.ventaListTotal.text = "Total: ${miVenta.PrecioFinal} $"

        }
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}


