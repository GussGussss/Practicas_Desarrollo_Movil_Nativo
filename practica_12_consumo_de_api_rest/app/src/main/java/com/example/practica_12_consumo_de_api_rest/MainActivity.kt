package com.example.practica_12_consumo_de_api_rest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica12.presentation.PokeViewModel
import com.example.practica12.presentation.PokemonDetailScreen
import com.example.practica12.presentation.PokemonsByTypeScreen
import com.example.practica12.presentation.TiposScreen
import com.example.practica_12_consumo_de_api_rest.ui.theme.Practica_12_consumo_de_api_restTheme

class MainActivity : ComponentActivity() {
    // Inicializa el ViewModel compartiéndolo a lo largo de la actividad
    private val viewModel: PokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // Grafo de navegación para enlazar las 3 pantallas de la app
            NavHost(navController = navController, startDestination = "tipos") {
                // Ruta 1: Tipos de Pokémon
                composable("tipos") {
                    TiposScreen(viewModel) { id ->
                        navController.navigate("pokemons/$id")
                    }
                }

                // Ruta 2: Pokémones por tipo seleccionado
                composable("pokemons/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: ""
                    PokemonsByTypeScreen(viewModel, id) { pokeId ->
                        navController.navigate("detalle/$pokeId")
                    }
                }

                // Ruta 3: Detalle de la Carta TCG del Pokémon
                composable("detalle/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: ""
                    PokemonDetailScreen(viewModel, id)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practica_12_consumo_de_api_restTheme {
        Greeting("Android")
    }
}