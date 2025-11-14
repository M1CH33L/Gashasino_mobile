package com.gashasino.mobile.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Usuario{
    var nombre by mutableStateOf("")
    var correo by mutableStateOf("")
    var edad by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var monedas by mutableStateOf(1000)
}