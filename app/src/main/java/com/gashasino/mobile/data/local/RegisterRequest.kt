package com.gashasino.mobile.data.local

data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val contrasena: String, // Asegúrate que el backend espera "password" o "contrasena"
    val edad: Int,
)