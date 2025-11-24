package com.gashasino.mobile.model

import com.google.gson.annotations.SerializedName

// --- Modelo para el Request de Login ---
data class LoginRequest(
    @SerializedName("email", alternate = ["correo"]) val correo: String,
    @SerializedName("password", alternate = ["contrasena"]) val contrasena: String
)

// --- Data Transfer Object del Usuario ---
data class UsuarioDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name", alternate = ["nombre"]) val nombre: String,
    @SerializedName("email", alternate = ["correo"]) val correo: String,
    @SerializedName("age", alternate = ["edad"]) val edad: Int, // CORREGIDO: Int (Numeric en Xano)
    @SerializedName("monedas") val monedas: Int,
    @SerializedName("password", alternate = ["contrasena"]) val contrasena: String?
)
