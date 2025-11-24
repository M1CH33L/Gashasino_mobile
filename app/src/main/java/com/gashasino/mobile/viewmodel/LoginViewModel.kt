package com.gashasino.mobile.viewmodel

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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Buscamos el usuario por correo
                val response = RetrofitInstance.api.findUserByEmail(login.correo)

                withContext(Dispatchers.Main) {
                    cargando = false
                    
                    // Xano devuelve una LISTA de usuarios que coinciden con el correo
                    if (response.isSuccessful && response.body() != null) {
                        val usuarios = response.body()!!
                        
                        if (usuarios.isNotEmpty()) {
                            // Obtenemos el primer usuario encontrado (debería ser único por correo)
                            val usuarioEncontrado = usuarios[0]
                            
                            // Verificamos la contraseña
                            // IMPORTANTE: En una app real esto no es seguro, pero es lo que podemos hacer
                            // con una API simple como Xano sin autenticación dedicada.
                            if (usuarioEncontrado.contrasena == login.contrasena) {
                                loginExitoso = true
                                usuarioLogueado = usuarioEncontrado
                                onSuccess(usuarioEncontrado)
                            } else {
                                loginExitoso = false
                                mensajeApi = "Contraseña incorrecta"
                                onError(mensajeApi)
                            }
                        } else {
                            loginExitoso = false
                            mensajeApi = "Usuario no encontrado"
                            onError(mensajeApi)
                        }
                    } else {
                        loginExitoso = false
                        mensajeApi = "Error en el servidor: ${response.code()}"
                        onError(mensajeApi)
                    }
                }
            } catch (e: Exception) {
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
