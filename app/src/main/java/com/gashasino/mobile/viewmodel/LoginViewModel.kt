package com.gashasino.mobile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gashasino.mobile.model.LoginMensajesError
import com.gashasino.mobile.model.LoginModel
import com.gashasino.mobile.repository.LoginRepository


class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    var login: LoginModel by mutableStateOf( repository.getLogin() )

    var mensajesError: LoginMensajesError by mutableStateOf( repository.getLoginMensajesError() )

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
        return repository.validacionCorreo()
    }

    fun comprobarContrasena(): Boolean{
        if(!repository.comprobarContrasena()) {
            mensajesError.correo = "El correo no es válido"
            return false
        } else {
            mensajesError.correo = ""
            return true
        }
        return repository.comprobarContrasena()
    }
}