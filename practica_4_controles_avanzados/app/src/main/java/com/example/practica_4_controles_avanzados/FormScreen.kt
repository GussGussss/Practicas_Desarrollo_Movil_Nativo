package com.example.practica_4_controles_avanzados

import android.R
import android.R.attr.text
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica_4_controles_avanzados.components.CustomCheckBox
import com.example.practica_4_controles_avanzados.components.CustomRadioButton
import com.example.practica_4_controles_avanzados.components.CustomSpinner
import com.example.practica_4_controles_avanzados.components.CustomSwitch
import org.w3c.dom.Text

@Composable
fun FormScreen(){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Practica 4: Componentes avanzados",
            fontSize = 22.sp,
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        Text("1. Switch", style = MaterialTheme.typography.titleMedium)
        CustomSwitch()

        HorizontalDivider()
        Text("2. RadioButton", style = MaterialTheme.typography.titleMedium)
        CustomRadioButton()

        HorizontalDivider()
        Text("3. CheckBox", style = MaterialTheme.typography.headlineMedium)
        CustomCheckBox()

        HorizontalDivider()
        Text("4. DatePicker", style = MaterialTheme.typography.headlineMedium)
        CustomSpinner()

        HorizontalDivider()
        Text("5. Switch", style = MaterialTheme.typography.headlineMedium)
        CustomSwitch()

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Formulario completo", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Probar Formulario")
        }
    }
}