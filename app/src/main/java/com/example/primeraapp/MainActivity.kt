package com.example.primeraapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONStringer

class MainActivity : AppCompatActivity() {
/*var JsonString: String = "{\n" +
        "  \"Estados\": [\n" +
        "    \"Soltero\",\n" +
        "    \"Casado\",\n" +
        "    \"Divorciado\"\n" +
            "]\n" +
        "}"
*/
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
    val btnIngresar: Button =findViewById(R.id.btnIngresar)
    val edtNombre: EditText = findViewById(R.id.edtNombre)
    val edtApellido: EditText = findViewById(R.id.edtApellido)
    val edtTelefono: EditText = findViewById(R.id.edtTelefono)
    val edtEdad: EditText = findViewById(R.id.edtEdad)
    val edtCorreo: EditText = findViewById(R.id.edtCorreo)
    btnIngresar.setOnClickListener{
        val nombre = edtNombre.getText().toString();
        val apellido = edtApellido.getText().toString();
        val telefono = edtTelefono.getText().toString();
        val edad = edtEdad.getText().toString();
        val correo = edtCorreo.getText().toString();
        val intent= Intent(this,Dashboard::class.java)
        intent.putExtra("Nombre", nombre)
        intent.putExtra("Apellido", apellido)
        intent.putExtra("Telefono", telefono)
        intent.putExtra("Edad", edad)
        intent.putExtra("Correo", correo)
        startActivity(intent)
        Toast.makeText(this,"Bienvenido",Toast.LENGTH_SHORT).show()
    }

    /* val stringArrayCarrers = arrayOf("Ingenieria de sistemas", "Medicina", "Ingenieria civil")

    val spinner: Spinner = findViewById(R.id.spinnerCarrer)
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stringArrayCarrers)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter

     val gson = Gson()
    val itemsResponse = gson.fromJson(JsonString, EstadosCivil::class.java)

    val spinnerEstado: Spinner = findViewById(R.id.spinnerState)
    val adapterEstado = ArrayAdapter(this, android.R.layout.simple_spinner_item, itemsResponse.Estados)
    adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerEstado.adapter = adapterEstado */
    setContentView(R.layout.activity_main)

    val spinnerEstado: Spinner = findViewById(R.id.spinnerState)

    // Ejecuta la coroutina para obtener los datos de la API
    CoroutineScope(Dispatchers.Main).launch {
        val estadosCivil = fetchEstadosCivil("https://c905e61696594c9f9c6a58d80581687b.api.mockbin.io/")
        estadosCivil?.let {
            val adapterEstado = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, it.EstadosCiviles)
            adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstado.adapter = adapterEstado
        } ?: run {
            Log.e("MainActivity", "No se recibieron datos")
        }
    }


    val spinnerCarrer: Spinner = findViewById(R.id.spinnerCarrer)

    // Ejecuta la coroutina para obtener los datos de la API
    CoroutineScope(Dispatchers.Main).launch {
        val carrerasU = fetchCarrerasU("https://d9e0db49d5c24b239e05d6b6e41b1801.api.mockbin.io/")
        carrerasU?.let {
            val adapterCarrer = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, it.CarrerasUniversitarias)
            adapterCarrer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCarrer.adapter = adapterCarrer
        } ?: run {
            Log.e("MainActivity", "No se recibieron datos")
        }
    }

    val spinnerDepartamentos: Spinner = findViewById(R.id.spinnerDepartment)

    // Ejecuta la coroutina para obtener los datos de la API
    CoroutineScope(Dispatchers.Main).launch {
        val departamentos = fetchDepartamentos("https://622bb2898e15433295a345659c28b866.api.mockbin.io/")
        departamentos?.let {
            val adapterDepartamentos = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, it.Departamentos)
            adapterDepartamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDepartamentos.adapter = adapterDepartamentos
        } ?: run {
            Log.e("MainActivity", "No se recibieron datos")
        }
    }



}

    // Función para realizar la solicitud HTTP en segundo plano
    private suspend fun fetchEstadosCivil(urlString: String): EstadosCivil? {
        return withContext(Dispatchers.IO) {
            var result: EstadosCivil? = null

            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    Log.d("MainActivity", "Respuesta JSON Estados Civil: $jsonString") // Imprime el JSON recibido
                    result = Gson().fromJson(jsonString, EstadosCivil::class.java)
                } else {
                    Log.e("MainActivity", "Error en la respuesta HTTP: ${connection.responseCode}")
                }
                connection.disconnect()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al obtener los datos", e)
            }

            result
        }
    }


    //Función para realizar la solicitud HTTP en segundo plano
    private suspend fun fetchCarrerasU(urlString: String): CarrerasU? {
        return withContext(Dispatchers.IO) {
            var result: CarrerasU? = null

            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    Log.d("MainActivity", "Respuesta JSON Carreras: $jsonString") // Imprime el JSON recibido
                    result = Gson().fromJson(jsonString, CarrerasU::class.java)
                } else {
                    Log.e("MainActivity", "Error en la respuesta HTTP: ${connection.responseCode}")
                }
                connection.disconnect()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al obtener los datos", e)
            }

            result
        }
    }

    private suspend fun fetchDepartamentos(urlString: String): Departamentos? {
        return withContext(Dispatchers.IO) {
            var result: Departamentos? = null

            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val jsonString = inputStream.bufferedReader().use { it.readText() }
                    Log.d("MainActivity", "Respuesta JSON Departamentos: $jsonString") // Imprime el JSON recibido
                    result = Gson().fromJson(jsonString, Departamentos::class.java)
                } else {
                    Log.e("MainActivity", "Error en la respuesta HTTP: ${connection.responseCode}")
                }
                connection.disconnect()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al obtener los datos", e)
            }

            result
        }
    }




}