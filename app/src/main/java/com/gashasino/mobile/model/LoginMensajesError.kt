package com.gashasino.mobile.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginMensajesError {
    var correo by mutableStateOf("")

    var contrasena by mutableStateOf("")
}