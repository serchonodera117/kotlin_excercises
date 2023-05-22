package com.example.examen2p

import android.os.Parcel
import android.os.Parcelable

public  class DatosProducto(): Parcelable {
    var idproducto = ""
    var NombreNegocio = ""
    var NombreProducto = ""
    var ExistenciaProducto = ""
    var DescripcionProducto = ""
    var Precio = ""

    constructor(parcel: Parcel): this(){
        idproducto = parcel.readString().toString()
        NombreNegocio = parcel.readString().toString()
        NombreProducto = parcel.readString().toString()
        ExistenciaProducto = parcel.readString().toString()
        DescripcionProducto = parcel.readString().toString()
        Precio = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idproducto)
        parcel.writeString(NombreNegocio)
        parcel.writeString(NombreProducto)
        parcel.writeString(ExistenciaProducto)
        parcel.writeString(DescripcionProducto)
        parcel.writeString(Precio)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatosProducto> {
        override fun createFromParcel(parcel: Parcel): DatosProducto {
            return DatosProducto(parcel)
        }

        override fun newArray(size: Int): Array<DatosProducto?> {
            return  arrayOfNulls(size)
        }
    }
}