package com.example.practica_2_calculadora_basica

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val CalcBackground = Color(0xFF0D9364)
val DarkScreen = Color(0xFF2C2C2E)
val ButtonColor = Color(0xFF0F9F6C)
val AppBackgroud = Color(0xFF1B2E4B)

@Composable
fun CalculatorScreen(){
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var opc by remember { mutableStateOf("") }

    fun onButtonClick(simbolo: String){
        when (simbolo){
            "C" -> {
                num1 = ""
            }
        }
    }
}