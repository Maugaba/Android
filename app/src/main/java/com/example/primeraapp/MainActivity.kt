package com.example.primeraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

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

    }
}