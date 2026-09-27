package com.example.miniproyecto_sistema_de_registro_de_estudiantes.components

import android.graphics.Outline
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.FormScreen

@Composable
fun ComponentDropDown(){
    var expanded by remember{ mutableStateOf(false) }
    var selectText by remember { mutableStateOf("Seleccionar opcion") }

    Box(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            value = selectText,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {expanded = true} ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            DropdownMenuItem(
                text = { Text("Ingenieria en Software") },
                onClick = {
                    selectText = "Ingenieria en Software"
                    expanded = false
                }
            )
                DropdownMenuItem(
                    text = { Text("Ciencias Biomedicas")},
                    onClick = {
                        selectText = "Ciencias Biomedicas"
                        expanded = false
                    }
                )
        }
    }
}

//ver el preview de lo que uno va haciendo
@Preview(showBackground = true)
@Composable
fun DropdownPreview(){
    ComponentDropDown()
}
