package com.example.mini_proyecto_red_social_local.iu

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
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
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mini_proyecto_red_social_local.data.Publicacion
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedSocialScreen(publicacionViewModel: PublicacionViewModel = viewModel()) {
    val context = LocalContext.current
    val publicaciones by publicacionViewModel.publicaciones.collectAsState()

    // Estados del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<String?>(null) }
    var fechaSeleccionada by remember { mutableStateOf("") }

    // Estado para la publicación seleccionada (ampliar imagen y detalle)
    var publicacionSeleccionada by remember { mutableStateOf<Publicacion?>(null) }

    // Lanzador para Galería: Guarda una copia física permanente en almacenamiento interno
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val uriLocal = guardarUriEnAlmacenamientoLocal(context, it)
            if (uriLocal != null) {
                fotoUri = uriLocal.toString()
            }
        }
    }

    // Lanzador para Cámara
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = guardarBitmapEnCache(context, it)
            fotoUri = uri.toString()
        }
    }

    // Permisos en tiempo de ejecución
    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { esConcedido ->
        if (esConcedido) {
            camaraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    // Selector de fecha nativo
    val calendario = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, anio, mes, dia ->
            fechaSeleccionada = String.format("%02d/%02d/%d", dia, mes + 1, anio)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

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
                            onClick = {
                                val tienePermiso = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                                if (tienePermiso) {
                                    camaraLauncher.launch(null)
                                } else {
                                    permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
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

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (fechaSeleccionada.isNotBlank()) "Fecha: $fechaSeleccionada" else "Seleccionar Fecha (Opcional)"
                        )
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
                                val fechaFinal = if (fechaSeleccionada.isNotBlank()) {
                                    fechaSeleccionada
                                } else {
                                    SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(Date())
                                }

                                publicacionViewModel.agregarPublicacion(
                                    titulo = titulo,
                                    descripcion = descripcion,
                                    fotoUri = fotoUri!!,
                                    fecha = fechaFinal
                                )

                                titulo = ""
                                descripcion = ""
                                fotoUri = null
                                fechaSeleccionada = ""
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
                        PublicacionItem(
                            publicacion = pub,
                            onClick = { publicacionSeleccionada = pub }
                        )
                    }
                }
            }
        }
    }

    publicacionSeleccionada?.let { pub ->
        Dialog(onDismissRequest = { publicacionSeleccionada = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage(
                        model = pub.fotoUri,
                        contentDescription = pub.titulo,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = pub.titulo, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = pub.descripcion, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Fecha: ${pub.fecha}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { publicacionSeleccionada = null },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}

@Composable
fun PublicacionItem(publicacion: Publicacion, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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

// Guarda la miniatura de la cámara en caché
private fun guardarBitmapEnCache(context: Context, bitmap: Bitmap): Uri {
    val archivo = File(context.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
    val stream = FileOutputStream(archivo)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    stream.flush()
    stream.close()
    return Uri.fromFile(archivo)
}

// Copia la imagen de la galería al almacenamiento interno de la app para que la URI persista siempre
private fun guardarUriEnAlmacenamientoLocal(context: Context, uri: Uri): Uri? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val archivo = File(context.filesDir, "galeria_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(archivo)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        Uri.fromFile(archivo)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}