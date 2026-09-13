package com.example.practica_1_hola_mundo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val texto = findViewById<EditText>(R.id.entradaTexto)
        val boton = findViewById<Button>(R.id.botonSaludar)
        val verMensaje = findViewById<TextView>(R.id.textView)

        boton.setOnClickListener() {
            val textIngresado = texto.text.toString()
            if (textIngresado.isEmpty()) {
                verMensaje.text="Escribe algo viejo"
            } else {
                verMensaje.text = "Que dice el viejo bien machin $textIngresado, si no escribiste un nombre, regresate a escribir un nombre, que esto es un saludo viejo"
            }
        }
    }
}