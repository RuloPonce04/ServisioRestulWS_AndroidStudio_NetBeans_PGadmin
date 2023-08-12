package com.example.movimientosjson

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var Nomina: EditText
    lateinit var Nombre: EditText
    lateinit var Puesto: EditText

    lateinit var BtnBuscar: Button
    lateinit var BtnLista: Button
    lateinit var BtnAgregar: Button
    lateinit var BtnModificar: Button
    lateinit var BtnEliminar: Button

    var Parametro="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.Parametro="Lunes"
        Nomina = findViewById(R.id.EdtNomina)
        Nombre = findViewById(R.id.EdtNombre)
        Puesto = findViewById(R.id.EdtPuesto)

        BtnBuscar = findViewById(R.id.BtnBuscar)

        BtnBuscar.setOnClickListener {
            consumirGETJSon()

        }

        BtnAgregar = findViewById(R.id.BtnAgregar)

        BtnAgregar.setOnClickListener {
            consumirPost()
        }

        BtnLista=findViewById(R.id.BtnLista)

        BtnLista.setOnClickListener {
            consumirGETLista()
        }

        BtnModificar=findViewById(R.id.BtnModificar)

        BtnModificar.setOnClickListener {
            consumirPut()
        }

        BtnEliminar=findViewById(R.id.BtnEliminar)

        BtnEliminar.setOnClickListener {
            consumirDELETE()
        }
    }

    private fun consumirGETLista() {
        val url = "http://192.168.100.120:8080/wsresttrabajadores/webresources/trabajador"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val listaJson = JSONArray(response)
                    println(listaJson)
                    for (i in 0 until listaJson.length()) {
                        val objeto = listaJson.getJSONObject(i)
                        println("Nomina: " + objeto["nomina"])
                        println("Nombre: " + objeto["nombre"])
                        println("Puesto: " + objeto["puesto"])
                    }
                } catch (e: Exception) {
                    println("ERROR: $e")
                }
            }
        ) { error -> println("ERROR al invocar al servicio... $error") }
        Volley.newRequestQueue(this).add(request)
    }

    private fun consumirGETJSon() {
        val nomina = Nomina.text.toString()
        val url = "http://192.168.100.120:8080/wsresttrabajadores/webresources/trabajador/${nomina}"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val nombre = jsonObject.optString("Nombre")
                    val puesto = jsonObject.optString("Puesto")

                    Nombre.setText(nombre)
                    Puesto.setText(puesto)

                } catch (e: Exception) {
                    println("ERROR: $e")
                }
            }
        ) { error -> println("ERROR al invocar al servicio... $error") }
        Volley.newRequestQueue(this).add(request)
    }


    private fun consumirPost() {

        val url = "http://192.168.100.120:8080/wsresttrabajadores/webresources/trabajador"
        val json = JSONObject()
        try {
            json.put("Nomina", "${Nomina.text.toString()}")
            json.put("Nombre", "${Nombre.text.toString()}")
            json.put("Puesto", "${Puesto.text.toString()}")
        } catch (error: JSONException) {
            println("ERROR $error")
        }
        val request = JsonObjectRequest(
            Request.Method.POST, url, json,
            { response ->
                try {
                    Toast.makeText(this,"El usuario ${Nomina.text.toString()} ${Nombre.text.toString()} ${Puesto.text.toString()}", android.widget.Toast.LENGTH_LONG).show()
                    println("----------------------------------------------")
                    println("Valor recibido POST " + response["mensaje"].toString())
                    println("----------------------------------------------")
                } catch (ERROR: JSONException) {
                    println("Error al recibir respuesta $ERROR")
                }
            }
        ) { error -> println("ERROR al invocar al servicio... $error") }
        Volley.newRequestQueue(this).add(request)
    }

    private fun consumirPut() {
        val url = "http://192.168.100.120:8080/wsresttrabajadores/webresources/trabajador"
        val json = JSONObject()
        try {
            json.put("Nomina", "${Nomina.text.toString()}")
            json.put("Nombre", "${Nombre.text.toString()}")
            json.put("Puesto", "${Puesto.text.toString()}")
        } catch (error: JSONException) {
            println("ERROR $error")
        }
        val request = JsonObjectRequest(
            Request.Method.PUT, url, json,
            { response ->
                try {
                    Toast.makeText(this,"Se modifico ${Nomina.text.toString()} ${Nombre.text.toString()} ${Puesto.text.toString()}", android.widget.Toast.LENGTH_LONG).show()
                    println("----------------------------------------------")
                    println("Valor recibido PUT " + response["mensaje"].toString())
                    println("----------------------------------------------")
                } catch (ERROR: JSONException) {
                    println("Error al recibir respuesta $ERROR")
                }
            }
        ) { error -> println("ERROR al invocar al servicio... $error") }
        Volley.newRequestQueue(this).add(request)
    }

    private fun consumirDELETE() {
        val nomina=Nomina.text.toString()
        val url = "http://192.168.100.120:8080/wsresttrabajadores/webresources/trabajador/${nomina}"
        val request = StringRequest(
            Request.Method.DELETE, url,
            { println("Registro eliminado") }
        ) { error -> println("ERROR al invocar al servicio... $error") }
        Volley.newRequestQueue(this).add(request)
        Toast.makeText(this,"El usuario  ${Nomina.text.toString()}, ${Nombre.text.toString()}, ${Puesto.text.toString()}, se ah eliminado", android.widget.Toast.LENGTH_LONG).show()
    }
}
