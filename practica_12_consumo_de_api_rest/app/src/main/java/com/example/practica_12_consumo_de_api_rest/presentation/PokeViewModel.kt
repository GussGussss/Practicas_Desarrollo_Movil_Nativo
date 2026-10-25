package com.example.practica12.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica12.data.NamedResource
import com.example.practica12.data.PokemonDetailResponse
import com.example.practica12.data.PokeRepository
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {
    private val repository = PokeRepository()

    // Estados para almacenar los datos que se muestran en la UI
    var tipos = mutableStateOf<List<NamedResource>>(emptyList())
    var pokemonsPorTipo = mutableStateOf<List<NamedResource>>(emptyList())
    var pokemonDetalle = mutableStateOf<PokemonDetailResponse?>(null)

    init {
        // Carga la lista inicial de tipos al abrir la app
        viewModelScope.launch {
            try { tipos.value = repository.getTypes() } catch (e: Exception) { }
        }
    }

    // Petición para traer la lista de pokémon según el tipo seleccionado
    fun cargarPokemonsPorTipo(id: String) {
        viewModelScope.launch {
            try { pokemonsPorTipo.value = repository.getPokemonsByType(id) } catch (e: Exception) { }
        }
    }

    // Petición para traer el detalle completo de un pokémon
    fun cargarDetallePokemon(id: String) {
        pokemonDetalle.value = null // Resetea el detalle anterior antes de cargar el nuevo
        viewModelScope.launch {
            try { pokemonDetalle.value = repository.getPokemonDetail(id) } catch (e: Exception) { }
        }
    }
}