package com.example.kotlin_volley_api


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//----Ocultar la barra de las acciones
        val actionBar = supportActionBar
        actionBar?.hide()

//----Ocultar barra de progreso
        progressBar1.visibility = View.GONE

//----Cargar datos del usuario (usuario y contraseña)
        val preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        editText.setText(preferencias.getString("usuario",""))
        editText2.setText(preferencias.getString("contrasena",""))

        if(preferencias.getString("usuario", "") != "")  {checkBox.isChecked = true}

//----Botón de registrar
        button2.setOnClickListener {
            val registro = Intent(this, Registrarme::class.java)
            startActivity(registro)
        }

//----Botón para loguearse
        button.setOnClickListener {
            progressBar1.visibility= View.VISIBLE;
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url: String = "https://registrosappinventor.000webhostapp.com/kotlin_login.php?usuario=${editText.text.toString()}&contrasena=${editText2.text.toString()}";
            //"https://registrosappinventor.000webhostapp.com/kotlin_login.php"
            //textView.text=url.toString()

            // Request a string response from the provided URL.



            val stringReq = StringRequest(Request.Method.GET, url,

                Response.Listener<String> { response ->
                    progressBar1.visibility= View.GONE;
                    var strResp = response.toString()
                    val jsonObj: JSONObject = JSONObject(strResp)
                    val jsonArray: JSONArray = jsonObj.getJSONArray("records")
                    if (jsonArray.length()==0)

                    {

                        Alerta("Usuarios","No esta registrado este usuario!")

                    } else {
                        if(checkBox.isChecked)
                        {
                            val editor = preferencias.edit()
                            editor.putString("usuario", editText.text.toString())
                            editor.putString("contrasena", editText2.text.toString())
                            editor.commit()
                        }else {
                            val editor = preferencias.edit()
                            editor.putString("usuario", "")
                            editor.putString("contrasena", "")
                            editor.commit()
                        }
                        val intento1 = Intent(this, Inicio::class.java)
                        startActivity(intento1)
                    }
                },
                Response.ErrorListener {
                    progressBar1.visibility= View.GONE;
                    Alerta("Red","Problemas con el Internet")
                })

            queue.add(stringReq)
        }
    }

    fun Alerta(Titulo:String, Mensaje:String)
    {
        val dialogBuilder = AlertDialog.Builder(this)
        // set message of alert dialog
        dialogBuilder.setMessage(Mensaje)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("OK", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(Titulo)
        // show alert dialog
        alert.show()
    }
}
