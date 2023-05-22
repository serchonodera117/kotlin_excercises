package com.example.examen2p

import android.os.Parcel
import android.os.Parcelable


public  class DatosPersona() :Parcelable {

        var idusuario = ""
        var NombreUsuario = ""
        var ApellidosUsuario = ""
        var ImagenUsuario = ""
        var MunicipioUsuario = ""
        var ColoniaUsuario = ""
        var DireccionUsuario = ""
        var CorreoUsuario = ""
        var ContrasenaUsuario = ""
        var TipoUsuario = ""
        var TelefonoUsuario = ""

        constructor(parcel: Parcel) : this() {
                idusuario = parcel.readString().toString()
                NombreUsuario = parcel.readString().toString()
                ApellidosUsuario = parcel.readString().toString()
                ImagenUsuario = parcel.readString().toString()
                MunicipioUsuario = parcel.readString().toString()
                ColoniaUsuario = parcel.readString().toString()
                DireccionUsuario = parcel.readString().toString()
                CorreoUsuario = parcel.readString().toString()
                ContrasenaUsuario = parcel.readString().toString()
                TipoUsuario = parcel.readString().toString()
                TelefonoUsuario = parcel.readString().toString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(idusuario)
                parcel.writeString(NombreUsuario)
                parcel.writeString(ApellidosUsuario)
                parcel.writeString(ImagenUsuario)
                parcel.writeString(MunicipioUsuario)
                parcel.writeString(ColoniaUsuario)
                parcel.writeString(DireccionUsuario)
                parcel.writeString(CorreoUsuario)
                parcel.writeString(ContrasenaUsuario)
                parcel.writeString(TipoUsuario)
                parcel.writeString(TelefonoUsuario)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<DatosPersona> {
                override fun createFromParcel(parcel: Parcel): DatosPersona {
                        return DatosPersona(parcel)
                }

                override fun newArray(size: Int): Array<DatosPersona?> {
                        return arrayOfNulls(size)
                }
        }

}