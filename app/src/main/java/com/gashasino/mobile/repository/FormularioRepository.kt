package com.gashasino.mobile.repository

import com.gashasino.mobile.model.FormularioModel
import com.gashasino.mobile.model.MensajesError

class  FormularioRepository(){

    private var formulario = FormularioModel()
    private var errores = MensajesError()

    fun getFormulario():  FormularioModel = formulario
    fun getMensajesError():  MensajesError = errores


    fun cambiarNombre(nuevoNombre: String) {
        formulario.nombre = nuevoNombre
    }

    fun validacionNombre(): Boolean {
        return formulario.nombre.isNotEmpty()
    }

    fun validacionCorreo(): Boolean {
        return formulario.correo.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
    }

    fun validacionEdad(): Boolean {
        val edadInt = formulario.edad.toIntOrNull()
        return edadInt != null && edadInt >= 18 && edadInt <= 120
    }

    fun validacionTerminos(): Boolean {
        return formulario.terminos
    }

    fun validacionContrasena(): Boolean {
        return formulario.contrasena.isNotEmpty()
    }


}