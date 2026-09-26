package com.example.practica_5_sharedpreferences.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager (context: Context) {
    //Archivo XML interno donde android guarda las preferencias
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences",
        Context.MODE_PRIVATE)

    //Claves (keys)
    companion object{
        const val KEY_USERNAME = "key_username"
        const val KEY_NOTIFICATIONS = "key_notifications"
        const val KEY_DARK_THEME = "key_dark_theme"
    }
    //metodos de guardado
    fun saveSettings(username: String, notifications: Boolean, darkTheme: Boolean){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME,username)
        editor.putBoolean(KEY_NOTIFICATIONS, notifications)
        editor.putBoolean(KEY_DARK_THEME, darkTheme)
        editor.apply()
    }

    //metodos de consulta
    fun getUsername(): String{
        return sharedPreferences.getString(KEY_USERNAME,"") ?: ""
    }
    fun getNotifications(): Boolean{
        return sharedPreferences.getBoolean(KEY_NOTIFICATIONS,false)
    }
    fun getDarkTheme(): Boolean{
        return sharedPreferences.getBoolean(KEY_DARK_THEME, false)
    }
    //borrado de preferencias (para el boton de reinicio)
    fun clearPreferences(){
        sharedPreferences.edit().clear().apply()
    }
}