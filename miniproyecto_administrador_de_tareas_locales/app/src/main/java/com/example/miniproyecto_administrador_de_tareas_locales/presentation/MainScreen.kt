package com.example.miniproyecto_administrador_de_tareas_locales.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.miniproyecto_administrador_de_tareas_locales.data.Tarea
import com.example.miniproyecto_administrador_de_tareas_locales.domain.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.tareaDao()
    val coroutineScope = rememberCoroutineScope()

    val tareas by dao.obtenerTodas().collectAsState(emptyList())

    var nuevoTitulo by remember { mutableStateOf("") }
    var tareaEnEdicion by remember { mutableStateOf<Tarea?>(null) }
    var menuExpandido by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Administrador de Tareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // Menú desplegable (Overflow Menu) requerido por la rúbrica
                    IconButton(onClick = { menuExpandido = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Opciones"
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpandido,
                        onDismissRequest = { menuExpandido = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Eliminar completadas") },
                            onClick = {
                                menuExpandido = false
                                coroutineScope.launch(Dispatchers.IO) {
                                    dao.eliminarCompletadas()
                                }
                            }
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Campo de texto para ingresar o modificar tareas
            OutlinedTextField(
                value = nuevoTitulo,
                onValueChange = { nuevoTitulo = it },
                label = { Text(if (tareaEnEdicion == null) "Nueva tarea" else "Editar tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón principal (Guardar o Actualizar)
            Button(
                onClick = {
                    if (nuevoTitulo.isNotBlank()) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val actual = tareaEnEdicion
                            if (actual == null) {
                                dao.insertar(Tarea(titulo = nuevoTitulo))
                            } else {
                                dao.actualizar(actual.copy(titulo = nuevoTitulo))
                            }
                            withContext(Dispatchers.Main) {
                                nuevoTitulo = ""
                                tareaEnEdicion = null
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (tareaEnEdicion == null) "Guardar Tarea" else "Actualizar Tarea")
            }

            if (tareaEnEdicion != null) {
                TextButton(
                    onClick = {
                        nuevoTitulo = ""
                        tareaEnEdicion = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar edición")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Lista de Tareas:", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Listado dinámico con LazyColumn (equivalente moderno a RecyclerView)[cite: 6, 8]
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tareas) { tarea ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Checkbox para marcar tarea como completada / pendiente
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Checkbox(
                                    checked = tarea.completada,
                                    onCheckedChange = { estadoSeleccionado ->
                                        coroutineScope.launch(Dispatchers.IO) {
                                            dao.actualizar(tarea.copy(completada = estadoSeleccionado))
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = tarea.titulo,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            // Botones de acción: Editar y Eliminar
                            Row {
                                IconButton(onClick = {
                                    tareaEnEdicion = tarea
                                    nuevoTitulo = tarea.titulo
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = {
                                    coroutineScope.launch(Dispatchers.IO) {
                                        dao.eliminar(tarea)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

