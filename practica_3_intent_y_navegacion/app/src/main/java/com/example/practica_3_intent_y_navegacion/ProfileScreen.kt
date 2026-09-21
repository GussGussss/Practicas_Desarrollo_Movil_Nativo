package com.example.practica_3_intent_y_navegacion

import androidx.compose.runtime.Composable

@Composable

fun ProfileScreen(
    nombre: String,
    correo: String,
    onBackClick: () -> unit
)