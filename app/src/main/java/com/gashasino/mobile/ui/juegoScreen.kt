// Podrías crear este en un nuevo archivo, ej. ui/JuegoScreen.kt
package com.gashasino.mobile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gashasino.mobile.model.Juego

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.gashasino.mobile.R // Asegúrate de importar tu R



@Composable
fun TarjetaJuego(juego: Juego, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = juego.imagenId),
                contentDescription = "Imagen del juego ${juego.nombre}",
                contentScale = ContentScale.Crop, // Para que la imagen llene el espacio
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = juego.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = juego.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


    val listaDeJuegos = listOf(
        Juego(
            "Gacha Game 1",
            "Una emocionante aventura RPG con héroes coleccionables.",
            R.drawable.blackjack
        ),
        Juego(
            "Puzzle Gacha",
            "Resuelve puzzles para desbloquear nuevos personajes.",
            R.drawable.casinowars
        ),
        Juego("Rhythm Gacha", "Un juego de ritmo con tus artistas favoritos.", R.drawable.poker),
        Juego(
            "Strategy Gacha",
            "Comanda tus unidades en este juego de estrategia.",
            R.drawable.ruleta
        ),
        Juego(
            "Strategy Gacha",
            "Comanda tus unidades en este juego de estrategia.",
            R.drawable.sicbo
        ),
        Juego(
            "Strategy Gacha",
            "Comanda tus unidades en este juego de estrategia.",
            R.drawable.slots
        ),
        // Agrega más juegos aquí
    )




@Composable
fun juegoScreen() {

    LazyVerticalGrid(
        // GridCells.Fixed(2) crea una rejilla con 2 columnas.
        // GridCells.Adaptive(180.dp) es más flexible y adapta el número de columnas
        // según el ancho de la pantalla, intentando que cada celda mida 180.dp.
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listaDeJuegos) { juego ->
            TarjetaJuego(juego = juego)
        }
    }
}