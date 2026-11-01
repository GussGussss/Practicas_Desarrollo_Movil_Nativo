package com.example.mini_proyecto_red_social_local.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_proyecto_red_social_local.data.AppDatabase
import com.example.mini_proyecto_red_social_local.data.Publicacion
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PublicacionViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).publicacionDao()

    val publicaciones: StateFlow<List<Publicacion>> = dao.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarPublicacion(titulo: String, descripcion: String, fotoUri: String, fecha: String) {
        viewModelScope.launch {
            val nuevaPublicacion = Publicacion(
                titulo = titulo,
                descripcion = descripcion,
                fotoUri = fotoUri,
                fecha = fecha
            )
            dao.insertar(nuevaPublicacion)
        }
    }
}