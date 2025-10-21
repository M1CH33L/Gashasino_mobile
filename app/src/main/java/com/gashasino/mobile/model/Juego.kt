// En app/src/main/java/com/gashasino/mobile/model/Juego.kt
package com.gashasino.mobile.model

import androidx.annotation.DrawableRes

data class Juego(
    val nombre: String,
    val descripcion: String,
    @DrawableRes val imagenId: Int // Usaremos un ID de recurso de imagen
)