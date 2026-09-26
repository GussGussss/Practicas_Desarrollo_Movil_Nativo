package com.example.practica_5_sharedpreferences

import android.R.attr.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica_5_sharedpreferences.data.PreferencesManager
import org.w3c.dom.Text

@Composable
fun FormScreen(){
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    //estados simples
    var username by remember { mutableStateOf("") }
    var notificationsEnabled by remember { mutableStateOf(false ) }
    var darkThemeEnabled by remember { mutableStateOf(false )}

    //carga los datos guardados
    LaunchedEffect(Unit) {
        username = preferencesManager.getUsername()
        notificationsEnabled = preferencesManager.getNotifications()
        darkThemeEnabled = preferencesManager.getDarkTheme()
    }

    //seleccionamos la paleta de colores
    val colorScheme = if (darkThemeEnabled){
        darkColorScheme()
    }else{
        lightColorScheme()
    }

    //aplicamos el tema directamente con MaterialTheme
    MaterialTheme(colorScheme = colorScheme) {
        //surface toma el color de fondo y el color del texto automaticamente
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Practica 5: Configuracion",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                HorizontalDivider()
                //1. Campo de texto para el usuario
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it},
                    label = {Text("Nombre de Usuario")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                //2. Switch para notificaciones
            }
        }
    }
}