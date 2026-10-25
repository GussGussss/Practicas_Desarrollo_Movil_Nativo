package com.example.practica12.data

import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz donde definimos las rutas (endpoints) de la PokeAPI
interface PokeApi {
    // Obtiene todos los tipos de pokémon
    @GET("type")
    suspend fun getTypes(): TypeListResponse

    // Obtiene la lista de pokémon filtrada por ID de tipo
    @GET("type/{id}")
    suspend fun getPokemonsByType(@Path("id") id: String): TypeDetailResponse

    // Obtiene los datos detallados de un pokémon por su ID
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: String): PokemonDetailResponse
}