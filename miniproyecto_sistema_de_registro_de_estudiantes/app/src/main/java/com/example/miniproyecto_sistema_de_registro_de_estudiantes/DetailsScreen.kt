package com.example.miniproyecto_sistema_de_registro_de_estudiantes

import android.telecom.Call
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat

@Composable
fun DetailsScreen(
    nombre: String,
    matricula: String,
    carrera: String,
    Turno: String,
    Status: String,
    onBackClick: () -> Unit
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Datos del Alumno",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(text = "Nombre: ", style = MaterialTheme.typography.labelLarge)
                    Text(text = nombre, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Matricula: ", style = MaterialTheme.typography.labelLarge)
                    Text(text = matricula, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Carrera: ", style = MaterialTheme.typography.labelLarge)
                    Text(text = carrera, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Turno: ", style = MaterialTheme.typography.labelLarge)
                    Text(text = Turno, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Status: ", style = MaterialTheme.typography.labelLarge)
                    Text(text = Status, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al registro")
            }
        }
    }
}