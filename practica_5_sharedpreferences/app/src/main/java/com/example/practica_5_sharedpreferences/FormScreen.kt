package com.example.practica_5_sharedpreferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.practica_5_sharedpreferences.data.PreferencesManager

@Composable
fun FormScreen(){
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    //estados simples
    var username by remember { mutableStateOf("") }
    var notificationsEnabled by remember { mutableStateOf(false ) }
    var darkThemeEnabled by remember { mutableStateOf(false )}

    //carga los datos guardados
}