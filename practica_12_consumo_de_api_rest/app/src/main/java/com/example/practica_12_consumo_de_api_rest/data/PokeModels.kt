package com.example.practica12.data

// Modelo base que guarda el nombre y la URL que devuelve la API
data class NamedResource(val name: String, val url: String) {
    // Extrae el ID sacando el número al final de la URL
    val id: String get() = url.trimEnd('/').substringAfterLast('/')
}

// Modelo para recibir la lista de tipos de pokémon
data class TypeListResponse(val results: List<NamedResource>)

// Modelos para obtener los pokémon pertenecientes a un tipo
data class TypeDetailResponse(val pokemon: List<PokemonSlot>)
data class PokemonSlot(val pokemon: NamedResource)

// Modelo principal con toda la información detallada de la carta
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<StatSlot>,
    val types: List<TypeSlot>
)

data class StatSlot(val base_stat: Int, val stat: NamedResource)
data class TypeSlot(val type: NamedResource)