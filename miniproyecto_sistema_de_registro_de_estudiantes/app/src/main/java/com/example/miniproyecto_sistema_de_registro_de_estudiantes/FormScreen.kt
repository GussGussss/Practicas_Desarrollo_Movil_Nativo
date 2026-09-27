package com.example.miniproyecto_sistema_de_registro_de_estudiantes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Funcion principal de la pantalla
@Composable
fun FormScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(45.dp)
    ) {
        Text(
            text = "Registo de estudiantes",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Aqui va lo demas")
    }
}

//ver el preview de lo que uno va haciendo
@Preview(showBackground = true)
@Composable
fun FormScreenPreview(){
    FormScreen()
}