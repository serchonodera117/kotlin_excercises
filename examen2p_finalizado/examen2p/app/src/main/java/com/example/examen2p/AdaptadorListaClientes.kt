package com.example.examen2p

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.estilo_lista_clientes.view.*

public  class AdaptadorListaClientes(val clientes: ArrayList<DatosCliente>):
    RecyclerView.Adapter<AdaptadorListaClientes.ViewHolder>(), Filterable {

    private lateinit var mListener: onItemClickListener
    var listaBusqueda = ArrayList<DatosCliente>()

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.estilo_lista_clientes, parent, false)
        return ViewHolder(v, mListener)
    }

    override fun getItemCount(): Int = clientes.size

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        return holder.render(clientes.get(i))
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        fun render(miCliente: DatosCliente) {
            Picasso.get().load(miCliente.ImagenCliente).into(itemView.imagenMiCliente)
            itemView.nombreMiCliente.text = miCliente.NombreCliente
            itemView.correoMiCliente.text = miCliente.CorreoCliente
            itemView.telefonoMiCliente.text = miCliente.TelefonoCliente
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            listaBusqueda = ArrayList()
            listaBusqueda.addAll(clientes)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
              val charSerch = constraint.toString()
                if(charSerch.isEmpty()){
                    listaBusqueda = clientes as ArrayList<DatosCliente>
                }else {
                    val resultList = ArrayList<DatosCliente>()
                    for(row in clientes){
                        if(row.NombreCliente.toLowerCase().contains(constraint.toString().toLowerCase())){
                            resultList.add(row)
                        }
                    }
                    listaBusqueda = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listaBusqueda
                return filterResults

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaBusqueda = results?.values as ArrayList<DatosCliente>
            }
        }
    }
}
