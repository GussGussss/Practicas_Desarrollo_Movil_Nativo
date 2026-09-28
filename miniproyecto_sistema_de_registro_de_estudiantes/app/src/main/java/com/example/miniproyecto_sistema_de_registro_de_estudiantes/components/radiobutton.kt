package com.example.miniproyecto_sistema_de_registro_de_estudiantes.components

import android.widget.RadioButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miniproyecto_sistema_de_registro_de_estudiantes.FormScreen

@Composable
fun ComponentRadioButton(){
    var selectedOption by remember { (mutableStateOf("Opcion 1")) }

    Row(verticalAlignment = Alignment.CenterVertically){
        RadioButton(
            selected = (selectedOption == "Matutino"),
            onClick = { selectedOption = "Matutino"}
        )
        Text(
            text = "Matutino",
            modifier = Modifier.clickable{selectedOption = "Matutino"}
        )
        Spacer(modifier = Modifier.width(16.dp))

        RadioButton(
            selected = (selectedOption == "Vespertino"),
            onClick = { selectedOption = "Vespertino"}
        )
        Text(
            text = "Vespertino",
            modifier = Modifier.clickable{selectedOption = "Vespertino"}
        )
    }
}

//ver el preview de lo que uno va haciendo
@Preview(showBackground = true)
@Composable
fun RadioButtonPreview(){
    ComponentRadioButton()
}