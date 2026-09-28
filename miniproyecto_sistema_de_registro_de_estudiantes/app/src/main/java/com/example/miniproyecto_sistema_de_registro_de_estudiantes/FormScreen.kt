package com.example.miniproyecto_sistema_de_registro_de_estudiantes

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.components.ComponentDropDown
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.components.ComponentRadioButton
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.components.ComponentSwitch
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.data.PreferencesManager
import kotlin.jvm.java

//Funcion principal de la pantalla
@Composable
fun FormScreen() {
    var nombre by remember() { mutableStateOf("") }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    var matricula by remember { mutableStateOf(preferencesManager.getMatricula())}

    LaunchedEffect(Unit) {
        matricula = preferencesManager.getMatricula()
    }

    Scaffold(
        containerColor = Color(0xFFE3F2Fd)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registo de estudiantes",
                fontSize = 22.sp,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre:") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it},
                label = { Text("Matricula:") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    "Seleccione la carrera a la que pertenece:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                ComponentDropDown()

                Spacer(modifier = Modifier.height(16.dp))
                Text("Seleccione turno:", style = MaterialTheme.typography.titleMedium)
                ComponentRadioButton()

                Spacer(modifier = Modifier.height(16.dp))
                Text("Status del estudiante:", style = MaterialTheme.typography.titleMedium)
                ComponentSwitch()

                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        preferencesManager.saveMatricula(matricula)
                        Toast.makeText(context, "Registro Completo", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, ActivityScreen::class.java).apply{
                            putExtra("EXTRA_NOMBRE", nombre)
                            putExtra("EXTRA_MATRICULA",matricula)
                        }
                        //mostrar la pantalla de los detalles
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1877F2)
                    )
                ) {
                    Text("Registrar")
                }
            }
        }
    }
}
//ver el preview de lo que uno va haciendo
@Preview(showBackground = true)
@Composable
fun FormScreenPreview(){
    FormScreen()
}