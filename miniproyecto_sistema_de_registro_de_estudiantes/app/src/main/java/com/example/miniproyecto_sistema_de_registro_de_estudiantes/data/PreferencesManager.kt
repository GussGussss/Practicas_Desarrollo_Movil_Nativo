package com.example.miniproyecto_sistema_de_registro_de_estudiantes.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.animation.SharedTransitionScope

class PreferencesManager (context: Context){
    //archivo xml interno que guarda las preferencias
    private val sharedPreferences: SharedPreferences =  context.getSharedPreferences("StudentPreferences",
        Context.MODE_PRIVATE)

    //clave (key)
    companion object{
        const val KEY_MATRICULA = "key_matricula"
    }

    fun saveMatricula(matricula: String){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_MATRICULA, matricula)
        editor.apply()
    }
    fun getMatricula(): String{
        return sharedPreferences.getString(KEY_MATRICULA,"") ?: ""
    }
}