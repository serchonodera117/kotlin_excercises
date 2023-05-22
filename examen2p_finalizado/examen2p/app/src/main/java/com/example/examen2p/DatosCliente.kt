package com.example.examen2p

import android.os.Parcel
import android.os.Parcelable

public  class DatosCliente() : Parcelable{
    var idcliente = ""
    var correoVendedor = ""
    var NombreCliente = ""
    var ApellidosCliente = ""
    var ImagenCliente= ""
    var MunicipioClientee= ""
    var DireccionCliente = ""
    var CorreoCliente = ""
    var TelefonoCliente = ""
    var TipoUsuario = ""

    constructor(parcel: Parcel) : this() {
        idcliente = parcel.readString().toString()
        correoVendedor = parcel.readString().toString()
        NombreCliente = parcel.readString().toString()
        ApellidosCliente = parcel.readString().toString()
        ImagenCliente = parcel.readString().toString()
        MunicipioClientee = parcel.readString().toString()
        DireccionCliente = parcel.readString().toString()
        CorreoCliente = parcel.readString().toString()
        TelefonoCliente = parcel.readString().toString()
        TipoUsuario = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idcliente)
        parcel.writeString(correoVendedor)
        parcel.writeString(NombreCliente)
        parcel.writeString(ApellidosCliente)
        parcel.writeString(ImagenCliente)
        parcel.writeString(MunicipioClientee)
        parcel.writeString(DireccionCliente)
        parcel.writeString(CorreoCliente)
        parcel.writeString(TelefonoCliente)
        parcel.writeString(TipoUsuario)
    }
    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<DatosCliente> {
        override fun createFromParcel(parcel: Parcel): DatosCliente {
            return DatosCliente(parcel)
        }

        override fun newArray(size: Int): Array<DatosCliente?> {
            return arrayOfNulls(size)
        }
    }
}