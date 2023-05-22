package com.example.examen2p

import android.content.DialogInterface
import android.os.Parcel
import android.os.Parcelable


public class DatosNegocio():Parcelable {

    var idnegocio = ""
    var CorreoUsuario  = ""
    var NombreNegocio  = ""
    var LogoUsuario  = ""
    var Efectivo  = ""
    var Tarjeta = ""
    var PagoOxxo = ""
    var Oferta_Producto  = ""
    var OfertaServicio  = ""
    var CategoriaNegocio  = ""
    var MunicipioNegocio = ""
    var ColoniaNegocio = ""
    var DireccionNegocio  = ""

    constructor(parcel: Parcel) : this() {
        idnegocio = parcel.readString().toString()
        CorreoUsuario = parcel.readString().toString()
        NombreNegocio = parcel.readString().toString()
        LogoUsuario = parcel.readString().toString()
        Efectivo = parcel.readString().toString()
        Tarjeta = parcel.readString().toString()
        PagoOxxo = parcel.readString().toString()
        Oferta_Producto = parcel.readString().toString()
        OfertaServicio = parcel.readString().toString()
        CategoriaNegocio = parcel.readString().toString()
        MunicipioNegocio = parcel.readString().toString()
        ColoniaNegocio = parcel.readString().toString()
        DireccionNegocio = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idnegocio)
        parcel.writeString(CorreoUsuario)
        parcel.writeString(NombreNegocio)
        parcel.writeString(LogoUsuario)
        parcel.writeString(Efectivo)
        parcel.writeString(Tarjeta)
        parcel.writeString(PagoOxxo)
        parcel.writeString(Oferta_Producto)
        parcel.writeString(OfertaServicio)
        parcel.writeString(CategoriaNegocio)
        parcel.writeString(MunicipioNegocio)
        parcel.writeString(ColoniaNegocio)
        parcel.writeString(DireccionNegocio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatosNegocio> {
        override fun createFromParcel(parcel: Parcel): DatosNegocio {
            return DatosNegocio(parcel)
        }

        override fun newArray(size: Int): Array<DatosNegocio?> {
            return arrayOfNulls(size)
        }
    }
}