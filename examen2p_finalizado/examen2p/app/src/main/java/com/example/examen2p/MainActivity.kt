package com.example.examen2p

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



//------Registrarse
        Registrarme.setOnClickListener{
            val registro = Intent(this, RegistroVendedor::class.java)
            startActivity(registro)
        }

//------Cargar datos de usuario(usuario y contraseña)
          val preferencias = getSharedPreferences("info", Context.MODE_PRIVATE)
          CorreoUsuario.setText(preferencias.getString("usuario", ""))
          ContrasenaUsuario.setText(preferencias.getString("contraseña", ""))

          if(preferencias.getString("usuario", "")!= "") Recordar.isChecked = true
            else Recordar.isChecked = false
//------Botón para loguearse
        Ingresar.setOnClickListener {
                  val queue = Volley.newRequestQueue(this)
                  val url: String = "https://registrosappinventor.000webhostapp.com/examen2p_login.php?" +
                          "CorreoUsuario=${CorreoUsuario.text.toString()}&ContrasenaUsuario=${ContrasenaUsuario.text.toString()}"

                  val respuestaString = StringRequest(Request.Method.GET, url,
                      { response ->
    //-----------------------------Obtención de un array json con los datos consultados
                                        var respuesta = response.toString()
                                        val jsonObj: JSONObject = JSONObject(respuesta)
                                        val jsonArray: JSONArray = jsonObj.getJSONArray("records")

                                            if(jsonArray.length()==0) {
                                                    Aviso("Usuarios", "no se encuentra registrado el usuario ${CorreoUsuario.text.toString()}")
                                            }
                                            else{
    //-----------------------------Modificacion/almacenado del usuario y contraseña(recordar)
                                                if(Recordar.isChecked) {
                                                    val editor = preferencias.edit()
                                                    editor.putString("usuario", CorreoUsuario.text.toString())
                                                    editor.putString("contraseña", ContrasenaUsuario.text.toString())
                                                    editor.commit()
                                                }else{
                                                val editor = preferencias.edit()
                                                editor.putString("usuario", "")
                                                editor.putString("contraseña", "")
                                                editor.commit()
                                            }

    //-----------------------------Almacenamiento del array en variable, y busqueda en atributos del objeto
                                                var datos = JSONObject(jsonArray.get(0).toString())
                                                var datosUsuario = DatosPersona()


                                                datosUsuario.idusuario = datos.getString("idusuario").toString()
                                                datosUsuario.NombreUsuario = datos.getString("NombreUsuario").toString()
                                                datosUsuario.ApellidosUsuario = datos.getString("ApellidosUsuario").toString()
                                                datosUsuario.ImagenUsuario = datos.getString("ImagenUsuario").toString()
                                                datosUsuario.MunicipioUsuario = datos.getString("MunicipioUsuario").toString()
                                                datosUsuario.ColoniaUsuario = datos.getString("ColoniaUsuario").toString()

                                                datosUsuario.DireccionUsuario = datos.getString("DireccionUsuario").toString()
                                                datosUsuario.CorreoUsuario= datos.getString("CorreoUsuario").toString()
                                                datosUsuario.ContrasenaUsuario = datos.getString("ContrasenaUsuario").toString()
                                                datosUsuario.TipoUsuario = datos.getString("TipoUsuario").toString()
                                                datosUsuario.TelefonoUsuario = datos.getString("TelefonoUsuario").toString()

                                        //iniciar sesion con datos del usuario
                                                val login = Intent(this,  Principal::class.java)
                                                  login.putExtra("datosUsuario", datosUsuario)
                                                 startActivity(login)


                                        //pasar correoVendedor(llave para consultar) al fragment mis clientes
                                            }
                  },
                      {
                          Aviso("Red", "No se pudo conectar a la base")
                      })
            queue.add(respuestaString)
        }
    }


    fun Aviso(Titulo:String, Mensaje:String)
    {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(Mensaje)
            .setCancelable(false)
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(Titulo)
        alert.show()
    }
}



