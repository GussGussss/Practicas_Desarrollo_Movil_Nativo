package com.example.miniproyecto_sistema_de_registro_de_estudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ActivityScreen : ComponentActivity(){
    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: "Sin nombre"
        val matricula = intent.getStringExtra("EXTRA_MATRICULA") ?: "Sin matricula"
        val carrera = intent.getStringExtra("EXTRA_CARRERA") ?: "Sin carrera"
        val turno = intent.getStringExtra("EXTRA_TURNO") ?: "Sin turno"
        val status = intent.getStringExtra("EXTRA_ESTATUS") ?: "Sin status"

        setContent {
            DetailsScreen(
                nombre = nombre,
                matricula = matricula,
                carrera = carrera,
                turno = turno,
                status = status,
                onBackClick = {finish()}
            )
        }
    }
}