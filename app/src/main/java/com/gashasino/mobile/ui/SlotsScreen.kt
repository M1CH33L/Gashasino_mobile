package com.gashasino.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gashasino.mobile.R
import com.gashasino.mobile.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val listaDeEmojis = listOf("🍒", "🍋", "🍊", "🔔", "💎")
val multiplicadores = mapOf("🍒" to 4, "🍋" to 6, "🍊" to 8, "🔔" to 20, "💎" to 40)

fun obtenerEmojiAleatorio() = listaDeEmojis.random()

@Composable
fun BotonApuesta(cantidad: Int, seleccionado: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (seleccionado) Color.Yellow else Color.Gray
        ),
        modifier = Modifier.border(
            width = if (seleccionado) 2.dp else 0.dp,
            color = if (seleccionado) Color.White else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Text(
            text = "$cantidad",
            color = if (seleccionado) Color.Black else Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SlotsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userViewModel: UserViewModel
) {
    // Recolectamos el balance. Al inicio será `null`.
    val balance by userViewModel.userMonedas.collectAsState()

    var estaGirando by remember { mutableStateOf(false) }
    var slot1 by remember { mutableStateOf(obtenerEmojiAleatorio()) }
    var slot2 by remember { mutableStateOf(obtenerEmojiAleatorio()) }
    var slot3 by remember { mutableStateOf(obtenerEmojiAleatorio()) }
    var apuestaSeleccionada by remember { mutableIntStateOf(0) }
    var mensajeResultado by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003300)),
        contentAlignment = Alignment.Center
    ) {
        // Si el balance es null, significa que todavía está cargando desde la base de datos.
        if (balance == null) {
            // Muestra un círculo de carga mientras se obtienen los datos.
            CircularProgressIndicator(color = Color.White)
        } else {
            // --- SOLUCIÓN: Crea una copia local que el compilador sabe que no es nula. ---
            val currentBalance = balance!! // El `!!` es seguro aquí por la comprobación de `balance == null`.

            // --- Una vez que tenemos el balance, mostramos la pantalla completa ---
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver atrás",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clickable { navController.navigate("juegoScreen") }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        // CORRECCIÓN: Usa la variable local `currentBalance`.
                        text = "Balance: $currentBalance",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mensajeResultado,
                        color = if (mensajeResultado.contains("GANASTE")) Color.Yellow else Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.maquina_slots),
                        contentDescription = "Máquina de Slots",
                        modifier = Modifier.size(600.dp)
                    )
                    Row(
                        modifier = Modifier.offset(y = (-15).dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(text = slot1, fontSize = 45.sp)
                        Text(text = slot2, fontSize = 45.sp)
                        Text(text = slot3, fontSize = 45.sp)
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        BotonApuesta(10, apuestaSeleccionada == 10) { apuestaSeleccionada = 10 }
                        BotonApuesta(100, apuestaSeleccionada == 100) { apuestaSeleccionada = 100 }
                    }

                    Button(
                        onClick = {
                            if (!estaGirando) {
                                mensajeResultado = ""
                                coroutineScope.launch {
                                    estaGirando = true

                                    userViewModel.addMonedas(-apuestaSeleccionada)

                                    val duracionGiro = 2000L
                                    val tiempoInicio = System.currentTimeMillis()

                                    while (System.currentTimeMillis() - tiempoInicio < duracionGiro) {
                                        slot1 = obtenerEmojiAleatorio()
                                        slot2 = obtenerEmojiAleatorio()
                                        slot3 = obtenerEmojiAleatorio()
                                        delay(75L)
                                    }
                                    slot1 = obtenerEmojiAleatorio()
                                    slot2 = obtenerEmojiAleatorio()
                                    slot3 = obtenerEmojiAleatorio()

                                    if (slot1 == slot2 && slot2 == slot3) {
                                        val multiplicador = multiplicadores[slot1] ?: 0
                                        val premio = apuestaSeleccionada * multiplicador
                                        userViewModel.addMonedas(premio)
                                        mensajeResultado = "¡GANASTE $premio!"
                                    } else {
                                        mensajeResultado = "Inténtalo de nuevo"
                                    }
                                    estaGirando = false
                                }
                            }
                        },
                        // CORRECCIÓN: Usa la variable local `currentBalance` en la condición.
                        enabled = !estaGirando && apuestaSeleccionada > 0 && currentBalance >= apuestaSeleccionada,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp)
                            .height(60.dp)
                    ) {
                        Text(
                            text = if (estaGirando) "GIRANDO..." else "GIRAR",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
