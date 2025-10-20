package com.gashasino.mobile.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginModel {
    var correo by mutableStateOf("")

    var contrasena by mutableStateOf("")

    var recordar by mutableStateOf(false)
}