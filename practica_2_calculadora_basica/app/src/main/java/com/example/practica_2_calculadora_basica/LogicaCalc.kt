package com.example.practica_2_calculadora_basica

import androidx.compose.ui.graphics.PathOperation

object LogicaCalc{
    fun calcular(num1: String, num2: String, operation: String): String{
        val n1 = num1.toDoubleOrNull() ?: return ""
        val n2 = num2.toDoubleOrNull() ?: return ""
        val result = when (operation){

        }
    }
}