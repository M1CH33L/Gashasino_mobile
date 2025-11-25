package com.gashasino.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gashasino.mobile.data.local.RegisterRequest
import com.gashasino.mobile.data.local.RetrofitInstance
import com.gashasino.mobile.model.FormularioModel
import com.gashasino.mobile.model.MensajesError
import com.gashasino.mobile.repository.FormularioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormularioViewModel : ViewModel() {
    private val repository = FormularioRepository()

    var formulario: FormularioModel by mutableStateOf( repository.getFormulario() )
    var mensajesError: MensajesError by mutableStateOf( repository.getMensajesError() )

    // Estados para la respuesta de la API
    var registroExitoso by mutableStateOf(false)
    var mensajeApi by mutableStateOf("")
    var cargando by mutableStateOf(false)

    fun registrarUsuario(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (!verificarFormulario()) return

        cargando = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // PASO 1: Verificar si el usuario ya existe
                val checkResponse = RetrofitInstance.api.findUserByEmail(formulario.correo)
                val usuariosEncontrados = checkResponse.body() ?: emptyList() // Si es null, usamos lista vacía

                // LOG PARA DEPURAR
                println("API CHECK: Body=$usuariosEncontrados")

                // --- SOLUCIÓN APLICADA ---
                // Buscamos manualmente si el correo que intentamos registrar (formulario.correo)
                // coincide exactamente con el campo 'email' de algún usuario en la lista devuelta.
                val usuarioYaExisteRealmente = usuariosEncontrados.any { usuario ->
                    // Comparamos ignorando mayúsculas/minúsculas para ser más seguros
                    usuario.correo.equals(formulario.correo, ignoreCase = true)
                }

                // Solo bloqueamos si ENCONTRAMOS el correo específico en la lista
                if (checkResponse.isSuccessful && usuarioYaExisteRealmente) {
                    withContext(Dispatchers.Main) {
                        cargando = false
                        registroExitoso = false
                        mensajeApi = "El correo ya está registrado."
                        onError(mensajeApi)
                    }
                    return@launch
                }

                // En FormularioViewModel.kt, dentro de registrarUsuario...

// PASO 2: Si no existe, procedemos al registro
                val edadInt = formulario.edad.toIntOrNull() ?: 18

// Creamos el objeto con los datos
                val nuevoUsuario = RegisterRequest(
                    nombre = formulario.nombre,
                    correo = formulario.correo,
                    contrasena = formulario.contrasena,
                    edad = edadInt
                )

// Enviamos el objeto
                val response = RetrofitInstance.api.register(nuevoUsuario)


                withContext(Dispatchers.Main) {
                    cargando = false
                    if (response.isSuccessful && response.body() != null) {
                        registroExitoso = true
                        mensajeApi = "Registro exitoso"
                        onSuccess()
                    } else {
                        registroExitoso = false
                        val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                        mensajeApi = "Error API (${response.code()}): $errorBody"
                        onError(mensajeApi)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    cargando = false
                    registroExitoso = false
                    mensajeApi = "Error de conexión: ${e.message}"
                    onError(mensajeApi)
                }
            }
        }
    }

    fun verificarFormulario(): Boolean {
        return verificarNombre() &&
                verificarCorreo() &&
                verificarEdad() &&
                verificarTerminos() &&
                verificarContrasena()
    }

    fun verificarNombre(): Boolean {
        if (!repository.validacionNombre()) {
            mensajesError.nombre = "El nombre no puede estar vacío"
            return false
        } else {
            mensajesError.nombre = ""
            return true
        }
    }

    fun verificarCorreo(): Boolean {
        if(!repository.validacionCorreo()) {
            mensajesError.correo = "El correo no es válido"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }
    }

    fun verificarEdad(): Boolean {
        if(!repository.validacionEdad()) {
            mensajesError.edad = "La edad debe ser un número entre 18 y 120"
            return false
        } else {
            mensajesError.edad = ""
            return true
        }
    }

    fun verificarTerminos(): Boolean {
        if(!repository.validacionTerminos()) {
            mensajesError.terminos = "Debes aceptar los términos"
            return false
        } else {
            mensajesError.terminos = ""
            return true
        }
    }

    fun verificarContrasena(): Boolean {
        if (!repository.validacionContrasena()) {
            mensajesError.contrasena = "La contraseña no puede estar vacía"
            return false
        } else {
            mensajesError.contrasena = ""
            return true
        }
    }
}
