package com.gashasino.mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gashasino.mobile.data.local.RetrofitInstance
import com.gashasino.mobile.model.UsuarioDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    // Estado para las monedas, gestionado manualmente ahora
    private val _userMonedas = MutableStateFlow<Int?>(null)
    val userMonedas: StateFlow<Int?> = _userMonedas.asStateFlow()

    // Datos del usuario actual en memoria
    var currentUserId: Int? = null
    var currentUserNombre: String = ""
    var currentUserCorreo: String = ""
    var currentUserEdad: Int = 0 // CORREGIDO: Ahora es Int para coincidir con UsuarioDto

    /**
     * Establece los datos del usuario una vez que se ha logueado exitosamente.
     */
    fun setUsuarioLogueado(usuario: UsuarioDto) {
        currentUserId = usuario.id
        currentUserNombre = usuario.nombre
        currentUserCorreo = usuario.correo
        currentUserEdad = usuario.edad // Ahora asignamos Int a Int, todo correcto
        _userMonedas.value = usuario.monedas
    }

    /**
     * Actualiza el saldo local y en el servidor.
     */
    fun addMonedas(cantidad: Int) {
        val userId = currentUserId ?: return
        val currentBalance = _userMonedas.value ?: return
        val nuevoSaldo = currentBalance + cantidad

        // Actualización optimista de la UI
        _userMonedas.value = nuevoSaldo

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // RetrofitInstance.api.actualizarMonedas(userId, nuevoSaldo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Refresca los datos del usuario desde el servidor (útil para PerfilScreen)
     */
    fun refreshUserData() {
        val userId = currentUserId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getUsuario(userId)
                if (response.isSuccessful && response.body() != null) {
                    val usuario = response.body()!!
                    // Actualizamos el estado
                    _userMonedas.value = usuario.monedas
                    currentUserNombre = usuario.nombre
                    currentUserCorreo = usuario.correo
                    currentUserEdad = usuario.edad
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
