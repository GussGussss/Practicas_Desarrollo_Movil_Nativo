package com.example.practica11

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReproductorScreen() {
    val context = LocalContext.current

    // Variables de estado para controlar la canción y la interfaz
    var audioUri by remember { mutableStateOf<Uri?>(null) }
    var nombreArchivo by remember { mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var posicionActual by remember { mutableFloatStateOf(0f) }
    var duracionTotal by remember { mutableFloatStateOf(0f) }

    // Actualiza la barra de progreso en segundo plano cada medio segundo mientras suena
    LaunchedEffect(isPlaying) {
        while (isPlaying && mediaPlayer != null) {
            posicionActual = mediaPlayer?.currentPosition?.toFloat() ?: 0f
            delay(500)
        }
    }

    // Selector de archivos para abrir el explorador nativo del cel
    val selectorAudio = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            audioUri = uri
            nombreArchivo = obtenerNombreArchivo(context, uri)

            // Liberamos el reproductor anterior si ya había uno abierto
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, uri)
            duracionTotal = mediaPlayer?.duration?.toFloat() ?: 0f
            posicionActual = 0f

            mediaPlayer?.start()
            isPlaying = true

            // Al terminar la canción se resetea el estado
            mediaPlayer?.setOnCompletionListener {
                isPlaying = false
                posicionActual = 0f
            }
        }
    }

    // Libera la memoria del reproductor al cerrar la app o cambiar de pantalla
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mini Reproductor MP3") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Muestra el nombre de la canción seleccionada
            Text(
                text = nombreArchivo ?: "Ningún archivo seleccionado",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Se muestra la barra de tiempo solo si ya cargamos un archivo
            if (audioUri != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Slider para ver y adelantar el progreso del audio
                    Slider(
                        value = posicionActual.coerceIn(0f, if (duracionTotal > 0f) duracionTotal else 1f),
                        onValueChange = { nuevaPos -> posicionActual = nuevaPos },
                        onValueChangeFinished = {
                            mediaPlayer?.seekTo(posicionActual.toInt())
                        },
                        valueRange = 0f..(if (duracionTotal > 0f) duracionTotal else 1f),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Tiempos (transcurrido vs total)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = formatearTiempo(posicionActual), style = MaterialTheme.typography.bodySmall)
                        Text(text = formatearTiempo(duracionTotal), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { selectorAudio.launch("audio/*") }) {
                Text("Buscar MP3 en SD/Memoria")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Controles de reproducción
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                // Play
                FloatingActionButton(
                    onClick = {
                        if (mediaPlayer != null && !isPlaying) {
                            mediaPlayer?.start()
                            isPlaying = true
                        }
                    }
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Reproducir")
                }

                // Pause
                FloatingActionButton(
                    onClick = {
                        if (mediaPlayer != null && isPlaying) {
                            mediaPlayer?.pause()
                            isPlaying = false
                        }
                    }
                ) {
                    Icon(Icons.Default.Pause, contentDescription = "Pausar")
                }

                // Stop (Reto)
                FloatingActionButton(
                    onClick = {
                        if (mediaPlayer != null) {
                            mediaPlayer?.pause()
                            mediaPlayer?.seekTo(0)
                            posicionActual = 0f
                            isPlaying = false
                        }
                    }
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "Detener")
                }
            }
        }
    }
}

// Función para extraer el nombre real del archivo usando la URI
fun obtenerNombreArchivo(context: Context, uri: Uri): String {
    var resultado = "Audio seleccionado"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) {
                resultado = it.getString(index)
            }
        }
    }
    return resultado
}

// Convierte los milisegundos a formato mm:ss para la vista
fun formatearTiempo(ms: Float): String {
    val totalSegundos = (ms / 1000).toInt()
    val minutos = totalSegundos / 60
    val segundos = totalSegundos % 60
    return String.format("%02d:%02d", minutos, segundos)
}