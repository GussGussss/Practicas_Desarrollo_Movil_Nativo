package com.example.miniproyecto_sistema_de_registro_de_estudiantes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.FormScreen

@Composable
fun ComponentSwitch(
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Inactivo / Activo:", fontSize = 16.sp)
        Switch(
            checked = isActive,
            onCheckedChange = { onActiveChange(it) }
        )
    }
}