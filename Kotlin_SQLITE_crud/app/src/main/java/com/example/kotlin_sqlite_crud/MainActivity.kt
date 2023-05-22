package com.example.kotlin_sqlite_crud

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guardarArticulo.setOnClickListener {
           try {
               val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
               val bd = admin.writableDatabase
               val registro = ContentValues()

               registro.put("codigo", codigo.text.toString())
               registro.put("descripcion", descripcion.text.toString())
               registro.put("precio", precio.text.toString())

               bd.insert("articulos", null, registro)
               bd.close()

               codigo.setText("")
               descripcion.setText("")
               precio.setText("")

               Toast.makeText(this, "Articulo guardado con éxito", Toast.LENGTH_SHORT).show()
           }catch (e: IOException){ Toast.makeText(this, "No se ha podido guardar :(", Toast.LENGTH_SHORT).show()}
        }

//---------Consulta por codigo
        consultaCodigo.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select descripcion, precio from articulos where codigo = ${codigo.text.toString()}", null)

            if(fila.moveToFirst()){
                descripcion.setText(fila.getString(0))
                precio.setText(fila.getString(1))
            }
            else{ Toast.makeText(this, "No existe un artículo con dicho código",  Toast.LENGTH_SHORT).show()}

            bd.close()
        }
//--------consulta por descripción
        consultaDescripcion.setOnClickListener{
               val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
               val bd = admin.writableDatabase
               val fila = bd.rawQuery("select codigo, precio from articulos where descripcion = '${descripcion.text.toString()}'",null)

               if(fila.moveToFirst()){
                   codigo.setText(fila.getString(0))
                   precio.setText(fila.getString(1))
               }else
               {
                   Toast.makeText(this, "No existe un artículo con esa descripción",  Toast.LENGTH_SHORT).show()
               }
               bd.close()
        }

 //-------- Borrado con código
        borrarCodigo.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
             val db = admin.writableDatabase
            val cant = db.delete("articulos", "codigo = ${codigo.text.toString()}", null)

            db.close()
            codigo.setText("")
            descripcion.setText("")
            precio.setText("")

            if(cant == 1) Toast.makeText(this, "Se ha borrado el artículo", Toast.LENGTH_SHORT).show()

            else Toast.makeText(this, "no se ha podido borrar el articulo", Toast.LENGTH_SHORT).show()
        }

//--------- Actualizar/editar registro
       editar.setOnClickListener {
           val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
           val db = admin.writableDatabase
           val registro = ContentValues()

           registro.put("descripcion", descripcion.text.toString())
           registro.put("precio", precio.text.toString())

           val cant = db.update("articulos", registro, "codigo = ${codigo.text.toString()}", null)
           db.close()

           if(cant == 1) Toast.makeText(this, "Se ha Actualizado el artículo", Toast.LENGTH_SHORT).show()
           else Toast.makeText(this, "no se ha podido Actualizar el articulo", Toast.LENGTH_SHORT).show()
       }
    }
}
