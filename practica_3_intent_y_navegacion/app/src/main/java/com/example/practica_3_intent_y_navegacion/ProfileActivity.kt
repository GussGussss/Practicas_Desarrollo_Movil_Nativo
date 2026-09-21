package com.example.practica_3_intent_y_navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //traemos los valores de Intent
        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: "Sin Nombre"
        val correo = intent.getStringExtra("EXTRA_CORREO") ?: "Sin Correo"

        setContent {
            ProfileScreen(
                nombre = nombre,
                correo = correo,
                onBackClick = {finish()}
            )
        }
    }
}