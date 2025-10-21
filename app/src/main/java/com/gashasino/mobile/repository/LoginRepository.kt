package com.gashasino.mobile.repository

import com.gashasino.mobile.model.LoginMensajesError
import com.gashasino.mobile.model.LoginModel
import com.gashasino.mobile.model.MensajesError

class LoginRepository() {
    private var login = LoginModel()
    private var error = LoginMensajesError()

    fun getLogin():  LoginModel = login
    fun getLoginMensajesError():  LoginMensajesError = error


    fun validacionCorreo(): Boolean {
        return login.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
    }

    fun comprobarContrasena(): Boolean {
        return login.contrasena.isNotEmpty()
    }
}