package com.gashasino.mobile.viewmodel

import android.util.Log // <-- IMPORTANTE: Importar Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gashasino.mobile.data.local.RetrofitInstance
import com.gashasino.mobile.model.LoginMensajesError
import com.gashasino.mobile.model.LoginModel
import com.gashasino.mobile.model.UsuarioDto
import com.gashasino.mobile.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {
    private val repository = LoginRepository()
    private val TAG = "LOGIN_DEBUG" // Etiqueta para filtrar en Logcat

    var login: LoginModel by mutableStateOf( repository.getLogin() )
    var mensajesError: LoginMensajesError by mutableStateOf( repository.getLoginMensajesError() )

    // Estados para la respuesta de la API
    var loginExitoso by mutableStateOf(false)
    var usuarioLogueado: UsuarioDto? by mutableStateOf(null)
    var mensajeApi by mutableStateOf("")
    var cargando by mutableStateOf(false)

    fun iniciarSesion(onSuccess: (UsuarioDto) -> Unit, onError: (String) -> Unit) {
        if (!verificarLogin()) return

        cargando = true

        // LOG 1: Ver qué escribió el usuario
        Log.d(TAG, "---------------- INICIO LOGIN ----------------")
        Log.d(TAG, "1. Input Usuario -> Correo: '${login.correo}' | Pass: '${login.contrasena}'")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.findUserByEmail(login.correo)

                withContext(Dispatchers.Main) {
                    cargando = false

                    if (response.isSuccessful) {
                        val usuarios = response.body() ?: emptyList()

                        // LOG 2: Ver qué devolvió la API en bruto
                        Log.d(TAG, "2. Respuesta API -> Código: ${response.code()} | Cantidad de usuarios: ${usuarios.size}")

                        // Imprimir todos los usuarios devueltos para ver si hay basura
                        usuarios.forEachIndexed { index, u ->
                            Log.d(TAG, "   User[$index] en lista -> Correo: '${u.correo}' | Pass: '${u.contrasena}'")
                        }

                        // Buscamos explícitamente si NUESTRO usuario está en la lista
                        val usuarioEncontrado = usuarios.find {
                            it.correo.equals(login.correo, ignoreCase = true)
                        }

                        if (usuarioEncontrado != null) {
                            // LOG 3: Ver qué usuario seleccionó el filtro
                            Log.d(TAG, "3. Usuario ENCONTRADO tras filtro -> Correo: '${usuarioEncontrado.correo}' | Pass DB: '${usuarioEncontrado.contrasena}'")

                            // LOG 4: La comparación crítica (Trim limpia espacios en blanco por si acaso)
                            val passInput = login.contrasena.trim()
                            val passDb = usuarioEncontrado.contrasena?.trim()

                            Log.d(TAG, "4. Comparando: '$passDb' == '$passInput'")

                            // NOTA: Usamos trim() aquí para ser más permisivos, o quítalo si quieres exactitud total
                            if (passDb == passInput) {
                                Log.d(TAG, "RESULTADO: ÉXITO")
                                loginExitoso = true
                                usuarioLogueado = usuarioEncontrado
                                onSuccess(usuarioEncontrado)
                            } else {
                                Log.e(TAG, "RESULTADO: FALLO DE PASSWORD")
                                loginExitoso = false
                                mensajeApi = "Contraseña incorrecta"
                                onError(mensajeApi)
                            }
                        } else {
                            Log.e(TAG, "RESULTADO: EL USUARIO NO ESTÁ EN LA LISTA DEVUELTA")
                            loginExitoso = false
                            mensajeApi = "Usuario no encontrado"
                            onError(mensajeApi)
                        }
                    } else {
                        Log.e(TAG, "ERROR SERVIDOR: ${response.code()} - ${response.message()}")
                        loginExitoso = false
                        mensajeApi = "Error en el servidor: ${response.code()}"
                        onError(mensajeApi)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "EXCEPCIÓN: ${e.message}")
                withContext(Dispatchers.Main) {
                    cargando = false
                    loginExitoso = false
                    mensajeApi = "Error de conexión: ${e.message}"
                    onError(mensajeApi)
                }
            }
        }
    }

    fun verificarLogin(): Boolean {
        return  verificarCorreo() &&
                comprobarContrasena()
    }

    fun verificarCorreo(): Boolean{
        if(!repository.validacionCorreo()) {
            mensajesError.correo = "El correo no esta ingresado"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }
    }

    fun comprobarContrasena(): Boolean{
        if(!repository.comprobarContrasena()) {
            mensajesError.contrasena = "La contraseña no es válida"
            return false
        } else {
            mensajesError.contrasena = ""
            return true
        }
    }
}
