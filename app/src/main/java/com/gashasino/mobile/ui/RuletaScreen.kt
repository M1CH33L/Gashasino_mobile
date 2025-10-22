package com.gashasino.mobile.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val rouletteNumbers = listOf(
    "0", "28", "9", "26", "30", "11", "7", "20", "32", "17", "5", "22", "34", "15", "3", "24", "36", "13", "1", "00", "27", "10", "25", "29", "12", "8", "19", "31", "18", "6", "21", "33", "16", "4", "23", "35", "14", "2"
)

private fun getRouletteColor(number: String): Color {
    return when (number) {
        "0", "00" -> Color(0xFF008000) // Green
        in listOf("1", "3", "5", "7", "9", "12", "14", "16", "18", "19", "21", "23", "25", "27", "30", "32", "34", "36") -> Color.Red
        else -> Color.Black
    }
}

@Composable
fun RuletaScreen(navController: NavController) {
    var balance by remember { mutableIntStateOf(1000) }
    var apuesta by remember { mutableStateOf("") }
    var numeroApostado by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var isSpinning by remember { mutableStateOf(false) }
    val rotationAngle = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var numeroGanador by remember { mutableStateOf<String?>(null) }
    var tipoApuesta by remember { mutableStateOf("numero") } // "numero", "rojo", "negro"


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF003300)), // Dark Green background
        contentAlignment = Alignment.Center
    ) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Balance: $balance",
                color = Color.White,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                RouletteWheel(rotationAngle = rotationAngle.value)
                RoulettePointer()
            }

            if (numeroGanador != null) {
                Text(
                    text = "Resultado: $numeroGanador",
                    color = Color.White,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Text(
                text = "Elige tu tipo de apuesta:",
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = { tipoApuesta = "numero" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (tipoApuesta == "numero") Color.Green else Color.DarkGray),
                    enabled = !isSpinning
                ) {
                    Text("Número", color = if (tipoApuesta == "numero") Color.Black else Color.White)
                }
                Button(
                    onClick = { tipoApuesta = "rojo" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (tipoApuesta == "rojo") Color.Red else Color.DarkGray),
                    enabled = !isSpinning
                ) {
                    Text("Rojo")
                }
                Button(
                    onClick = { tipoApuesta = "negro" },
                    colors = ButtonDefaults.buttonColors(containerColor = if (tipoApuesta == "negro") Color.Black else Color.DarkGray),
                    enabled = !isSpinning
                ) {
                    Text("Negro", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (tipoApuesta == "numero") {
                OutlinedTextField(
                    value = numeroApostado,
                    onValueChange = { numeroApostado = it },
                    label = { Text("Tu número (0-36 o 00)", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.Green
                    ),
                    singleLine = true,
                    enabled = !isSpinning
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = apuesta,
                onValueChange = { apuesta = it },
                label = { Text("Tu apuesta", color = Color.Gray) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.Green
                ),
                singleLine = true,
                enabled = !isSpinning
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val apuestaInt = apuesta.toIntOrNull()

                    if (apuestaInt == null || apuestaInt <= 0) {
                        mensaje = "Por favor, introduce una apuesta válida."
                    } else if (apuestaInt > balance) {
                        mensaje = "No tienes suficiente saldo para esta apuesta."
                    } else {
                        var isValidBet = true
                        if (tipoApuesta == "numero") {
                            val isValidNumber = numeroApostado == "00" || (numeroApostado.toIntOrNull() in 0..36)
                            if (!isValidNumber) {
                                mensaje = "Por favor, elige un número entre 0 y 36 (o 00)."
                                isValidBet = false
                            }
                        }

                        if (isValidBet) {
                            scope.launch {
                                isSpinning = true
                                mensaje = ""
                                balance -= apuestaInt

                                val winningIndex = Random.nextInt(0, rouletteNumbers.size)
                                val winner = rouletteNumbers[winningIndex]
                                val anglePerSlot = 360f / rouletteNumbers.size
                                val targetAngle = (360 * (5..8).random()) - (winningIndex * anglePerSlot) - (anglePerSlot / 2f) + rotationAngle.value

                                rotationAngle.animateTo(
                                    targetValue = targetAngle,
                                    animationSpec = tween(durationMillis = 4000)
                                )
                                numeroGanador = winner

                                var hasWon = false
                                var ganancia = 0

                                when (tipoApuesta) {
                                    "numero" -> {
                                        if (winner == numeroApostado) {
                                            hasWon = true
                                            ganancia = apuestaInt * 36
                                        }
                                    }
                                    "rojo" -> {
                                        if (getRouletteColor(winner) == Color.Red) {
                                            hasWon = true
                                            ganancia = apuestaInt * 2
                                        }
                                    }
                                    "negro" -> {
                                        if (getRouletteColor(winner) == Color.Black) {
                                            hasWon = true
                                            ganancia = apuestaInt * 2
                                        }
                                    }
                                }

                                if (hasWon) {
                                    balance += ganancia
                                    mensaje = "¡Felicidades! Has ganado $ganancia."
                                } else {
                                    mensaje = "Lo sentimos, has perdido. El número era $winner."
                                }
                                isSpinning = false
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                enabled = !isSpinning
            ) {
                Text(
                    text = "Girar",
                    color = Color.Black,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensaje,
                color = if (mensaje.contains("Felicidades")) Color.Green else Color.Red,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun RoulettePointer() {
    Canvas(modifier = Modifier.size(32.dp)) {
        val path = Path()
        path.moveTo(size.width / 2, size.height) // Tip pointing down
        path.lineTo(0f, 0f)
        path.lineTo(size.width, 0f)
        path.close()
        drawPath(path, color = Color(0xFFFDD835)) // Gold color
        drawPath(
            path,
            style = Stroke(width = 2.dp.toPx()),
            color = Color.Black.copy(alpha = 0.6f)
        )
    }
}


@Composable
fun RouletteWheel(rotationAngle: Float) {
    val anglePerSlot = 360f / rouletteNumbers.size
    val textPaint = remember {
        android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 35f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
    }

    Canvas(modifier = Modifier
        .fillMaxWidth(0.8f)
        .aspectRatio(1f)
        .padding(16.dp)
    ) {
        val outerRadius = size.minDimension / 2
        val innerRadius = outerRadius * 0.75f
        val centerCircleRadius = outerRadius * 0.15f

        rotate(degrees = rotationAngle, pivot = center) {
            // Draw colored slots
            rouletteNumbers.forEachIndexed { index, number ->
                val startAngle = (anglePerSlot * index) - 90f - (anglePerSlot / 2f)
                drawArc(
                    color = getRouletteColor(number),
                    startAngle = startAngle,
                    sweepAngle = anglePerSlot,
                    useCenter = true
                )
            }

            // Draw divider lines
            rouletteNumbers.forEachIndexed { index, _ ->
                val angle = (anglePerSlot * index) - 90f - (anglePerSlot / 2f)
                val lineEndX = center.x + outerRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
                val lineEndY = center.y + outerRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
                drawLine(
                    color = Color.DarkGray,
                    start = center,
                    end = Offset(lineEndX, lineEndY),
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Draw numbers (rotated)
            rouletteNumbers.forEachIndexed { index, number ->
                val textAngle = (anglePerSlot * index) - 90f
                val textRadius = (outerRadius + innerRadius) / 2
                val x = (center.x + textRadius * cos(Math.toRadians(textAngle.toDouble()))).toFloat()
                val y = (center.y + textRadius * sin(Math.toRadians(textAngle.toDouble()))).toFloat()

                drawIntoCanvas {
                    it.nativeCanvas.save()
                    it.nativeCanvas.rotate(textAngle + 90, x, y)
                    it.nativeCanvas.drawText(number, x, y, textPaint)
                    it.nativeCanvas.restore()
                }
            }
        }

        // Draw borders and center hub
        drawCircle(color = Color.Gray, style = Stroke(width = 6.dp.toPx()))
        drawCircle(radius = centerCircleRadius, color = Color(0xFFC0C0C0)) // Silver-like center
        drawCircle(radius = centerCircleRadius * 0.7f, color = Color.DarkGray)
    }
}
