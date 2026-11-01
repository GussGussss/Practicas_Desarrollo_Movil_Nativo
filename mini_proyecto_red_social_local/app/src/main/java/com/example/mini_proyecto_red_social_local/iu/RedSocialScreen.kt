package com.example.mini_proyecto_red_social_local.iu

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mini_proyecto_red_social_local.data.Publicacion
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedSocialScreen(publicacionViewModel: PublicacionViewModel = viewModel()) {
    val context = LocalContext.current
    val publicaciones by publicacionViewModel.publicaciones.collectAsState()

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<String?>(null) }

    // Lanzador para Galería (Práctica 11)
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { fotoUri = it.toString() }
    }

    // Lanzador para Cámara (Práctica 10)
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = guardarBitmapEnCache(context, it)
            fotoUri = uri.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Red Social Local") },
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
                .padding(16.dp)
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nueva Publicación", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { camaraLauncher.launch(null) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Cámara")
                        }

                        Button(
                            onClick = { galeriaLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Galería")
                        }
                    }

                    fotoUri?.let { uri ->
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = uri,
                            contentDescription = "Vista previa",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (titulo.isNotBlank() && descripcion.isNotBlank() && fotoUri != null) {
                                val fechaActual = SimpleDateFormat(
                                    "dd/MM/yyyy hh:mm a",
                                    Locale.getDefault()
                                ).format(Date())

                                publicacionViewModel.agregarPublicacion(
                                    titulo = titulo,
                                    descripcion = descripcion,
                                    fotoUri = fotoUri!!,
                                    fecha = fechaActual
                                )

                                titulo = ""
                                descripcion = ""
                                fotoUri = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = titulo.isNotBlank() && descripcion.isNotBlank() && fotoUri != null
                    ) {
                        Text("Guardar Publicación")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Publicaciones", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            if (publicaciones.isEmpty()) {
                Text(
                    "No hay publicaciones aún. ¡Crea una!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(publicaciones) { pub ->
                        PublicacionItem(publicacion = pub)
                    }
                }
            }
        }
    }
}

@Composable
fun PublicacionItem(publicacion: Publicacion) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = publicacion.fotoUri,
                contentDescription = publicacion.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = publicacion.titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = publicacion.descripcion, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = publicacion.fecha,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

private fun guardarBitmapEnCache(context: Context, bitmap: Bitmap): Uri {
    val archivo = File(context.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
    val stream = FileOutputStream(archivo)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    stream.flush()
    stream.close()
    return Uri.fromFile(archivo)
}