package com.example.primeraapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre: TextView=findViewById(R.id.txtNombre)
        val txtApellido: TextView=findViewById(R.id.textApellido)
        val txtTelefono: TextView=findViewById(R.id.textTelefono)
        val txtEdad: TextView=findViewById(R.id.textEdad)
        val txtCorreo: TextView=findViewById(R.id.textCorreo)

        val nombre= intent.getStringExtra("Nombre")
        val apellido= intent.getStringExtra("Apellido")
        val telefono= intent.getStringExtra("Telefono")
        val edad= intent.getStringExtra("Edad")
        val correo= intent.getStringExtra("Correo")

        txtNombre.text=nombre
        txtApellido.text=apellido
        txtTelefono.text=telefono
        txtEdad.text=edad
        txtCorreo.text=correo

    }
}