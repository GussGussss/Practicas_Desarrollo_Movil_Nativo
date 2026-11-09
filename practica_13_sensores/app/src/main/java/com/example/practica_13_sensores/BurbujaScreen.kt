package com.example.practica13

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BurbujaScreen(sensorManager: SensorManager) {

    // 1. Estados para los 3 ejes cartesiano (X, Y, Z)
    var ejeX by remember { mutableFloatStateOf(0f) }
    var ejeY by remember { mutableFloatStateOf(0f) }
    var ejeZ by remember { mutableFloatStateOf(0f) }

    // Estado para controlar si hubo una sacudida
    var estaSacudido by remember { mutableStateOf(false) }

    // Animación de cambio de color de fondo al sacudir
    val colorFondo by animateColorAsState(
        targetValue = if (estaSacudido) Color(0xFF8B0000) else Color.DarkGray, // Rojo oscuro si sacude, gris si normal
        animationSpec = tween(durationMillis = 300),
        label = "ColorAnimado"
    )

    // Configuración de sensibilidad para la burbuja
    val sensibilidad = 50f
    val radioMaximo = 250f
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    // Temporizador para restablecer el mensaje de sacudida
    LaunchedEffect(estaSacudido) {
        if (estaSacudido) {
            delay(1500) // Mantiene la alerta visible por 1.5 segundos
            estaSacudido = false
        }
    }

    // Listener del sensor acelerómetro
    DisposableEffect(Unit) {
        var ultimoTiempoSacudida: Long = 0

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    // Obtener valores brutos de los 3 ejes (X, Y, Z)
                    val rawX = it.values[0]
                    val rawY = it.values[1]
                    val rawZ = it.values[2]

                    ejeX = rawX
                    ejeY = rawY
                    ejeZ = rawZ

                    // Lógica para mover la burbuja (Ejes X e Y)
                    val posX = -rawX * sensibilidad
                    val posY = rawY * sensibilidad
                    val distancia = sqrt((posX * posX) + (posY * posY))

                    if (distancia > radioMaximo) {
                        val proporcion = radioMaximo / distancia
                        offsetX = posX * proporcion
                        offsetY = posY * proporcion
                    } else {
                        offsetX = posX
                        offsetY = posY
                    }

                    // 2. Lógica Matemática de Detección de Sacudida (Shake Detection)
                    val fuerzaAceleracion = sqrt((rawX * rawX) + (rawY * rawY) + (rawZ * rawZ))
                    val umbralSacudida = 15.0f // Umbral en m/s^2 (La gravedad normal es ~9.81 m/s^2)
                    val tiempoActual = System.currentTimeMillis()

                    if (fuerzaAceleracion > umbralSacudida) {
                        if (tiempoActual - ultimoTiempoSacudida > 500) { // Prevenir múltiples activaciones consecutivas
                            ultimoTiempoSacudida = tiempoActual
                            estaSacudido = true
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_GAME)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nivel de Burbuja + Sensores") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorFondo),
            contentAlignment = Alignment.Center
        ) {
            // Nivel de Burbuja Visual
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(4.dp, Color.White, CircleShape)
                    .background(Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .border(2.dp, Color.Red, CircleShape)
                )

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .size(40.dp)
                        .background(if (estaSacudido) Color.Red else Color(0xFF00FF00), CircleShape)
                )
            }

            // Mensaje Flotante de Alerta al Sacudir
            if (estaSacudido) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                ) {
                    Text(
                        text = "¡Dispositivo Sacudido!",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }
            }

            // Despliegue dinámico de las 3 coordenadas (X, Y, Z)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Eje X: ${"%.2f".format(ejeX)} m/s²",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = "Eje Y: ${"%.2f".format(ejeY)} m/s²",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = "Eje Z: ${"%.2f".format(ejeZ)} m/s²",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}