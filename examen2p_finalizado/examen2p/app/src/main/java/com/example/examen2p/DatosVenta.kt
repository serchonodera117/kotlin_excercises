package com.example.examen2p

import android.os.Parcel
import android.os.Parcelable

public class DatosVenta(): Parcelable {
    var idventa = ""
    var correoVendedor = ""
    var idCliente = ""
    var NombreCliente = ""
    var ApellidosCliente = ""
    var idnegocio = ""
    var NombreNegocio = ""
    var idproducto = ""
    var NombreProducto = ""
    var DescripcionProducto = ""
    var PrecioProducto = ""
    var CantidadProducto = ""
    var PrecioFinal = ""
    var Existencia = ""

    constructor(parcel: Parcel) : this() {
        idventa = parcel.readString().toString()
        correoVendedor = parcel.readString().toString()
        idCliente = parcel.readString().toString()
        NombreCliente = parcel.readString().toString()
        ApellidosCliente = parcel.readString().toString()
        idnegocio = parcel.readString().toString()
        NombreNegocio = parcel.readString().toString()
        idproducto = parcel.readString().toString()
        NombreProducto = parcel.readString().toString()
        DescripcionProducto = parcel.readString().toString()
        PrecioProducto = parcel.readString().toString()
        CantidadProducto = parcel.readString().toString()
        PrecioFinal = parcel.readString().toString()
        Existencia = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idventa)
        parcel.writeString(correoVendedor)
        parcel.writeString(idCliente)
        parcel.writeString(NombreCliente)
        parcel.writeString(ApellidosCliente)
        parcel.writeString(idnegocio)
        parcel.writeString(NombreNegocio)
        parcel.writeString(idproducto)
        parcel.writeString(NombreProducto)
        parcel.writeString(DescripcionProducto)
        parcel.writeString(PrecioProducto)
        parcel.writeString(CantidadProducto)
        parcel.writeString(PrecioFinal)
        parcel.writeString(Existencia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatosVenta> {
        override fun createFromParcel(parcel: Parcel): DatosVenta {
            return DatosVenta(parcel)
        }

        override fun newArray(size: Int): Array<DatosVenta?> {
            return arrayOfNulls(size)
        }
    }


}