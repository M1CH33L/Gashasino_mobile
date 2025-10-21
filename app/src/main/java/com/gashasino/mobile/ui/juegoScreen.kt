// Podrías crear este en un nuevo archivo, ej. ui/JuegoScreen.kt
package com.gashasino.mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gashasino.mobile.model.Juego
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
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
            "blackJack",
            "Maldito crupier, devuelveme la colegiatura de mis hijos",
            R.drawable.blackjack
        ),
        Juego(
            "Casino wars",
            "Consigue la carta mas alta y ganale al crupier",
            R.drawable.casinowars
        ),
        Juego("Poker",
              "No confies en nadie, ni en tus propias cartas",
              R.drawable.poker),
        Juego(
            "Ruleta",
            "Gira, gira y gira la ruleta",
            R.drawable.ruleta
        ),
        Juego(
            "Sic bo",
            "Busca la mejor combinacion de dados y robale la plata a los desarolladores",
            R.drawable.sicbo
        ),
        Juego(
            "Slots",
            "por favor diosito, dame un triple 7",
            R.drawable.slots
        ),
        // Agrega más juegos aquí
    )



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun juegoScreen(navController: NavController) {
    var monedasDelJugador = 1000 // Valor de ejemplo
    var selectedIcon by remember { mutableStateOf("Menú") } // Estado para el ícono seleccionado
    var menuAbierto by remember { mutableStateOf(false) } // Estado para el menú desplegable
    // Nota: El texto "Menú" se repite. Para un sistema real, usarías un identificador único.

    val simbolopeso = "$"

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ga" + simbolopeso + "ha" + simbolopeso + "ino",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.Gray.copy(alpha = 0.5f), shape = CircleShape)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$monedasDelJugador",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.moneda),
                        contentDescription = "Monedas",
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        },
        bottomBar = {
            // Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box { // Box para anclar el DropdownMenu
                    FooterIcon(
                        icon = Icons.Default.Menu,
                        text = "Menú",
                        isSelected = selectedIcon == "Menú",
                        onClick = {
                            selectedIcon = "Menú"
                            menuAbierto = true // Abrir el menú al hacer clic
                        }
                    )
                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false } // Cerrar al hacer clic fuera
                    ) {
                        DropdownMenuItem(
                            text = { Text("Registrarse") },
                            onClick = {
                                navController.navigate("Formulario")
                                menuAbierto = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Iniciar Sesión") },
                            onClick = {
                                navController.navigate("Login")
                                menuAbierto = false
                            }
                        )
                    }
                }
                FooterIcon(icon = Icons.Default.ShoppingCart, text = "Tienda", isSelected = selectedIcon == "Tienda", onClick = { selectedIcon = "Tienda" })
                FooterIcon(icon = Icons.Default.Home, text = "Inicio", isSelected = selectedIcon == "Inicio", onClick = { selectedIcon = "Inicio" })
                FooterIcon(icon = Icons.Default.Star, text = "Gachapón", isSelected = selectedIcon == "Gachapón", onClick = { selectedIcon = "Gachapón" })
                FooterIcon(icon = Icons.Default.AccountCircle, text = "Perfil", isSelected = selectedIcon == "Perfil", onClick = { selectedIcon = "Perfil" })
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(64.dp), // Reducido para mejor espaciado
            modifier = Modifier.padding(innerPadding) // Aplica el padding del Scaffold
        ) {
            items(items = listaDeJuegos, key = { it.nombre }) { juego ->
                TarjetaJuego(juego = juego)
            }
        }
    }
}

@Composable
fun FooterIcon(
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val size = if (isSelected) 40.dp else 28.dp
    val backgroundModifier = if (isSelected) {
        Modifier
            .border(2.dp, Color.Green, RoundedCornerShape(8.dp))
    } else {
        Modifier
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(backgroundModifier)
            .padding(8.dp) // Padding interno para que el contenido no toque el borde
    ) {
        Icon(
            imageVector = icon, contentDescription = text, tint = Color.White, modifier = Modifier.size(size)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}


