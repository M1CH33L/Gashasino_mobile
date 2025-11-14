package com.gashasino.mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gashasino.mobile.data.local.AppDatabase
import com.gashasino.mobile.data.local.UserEntity
import com.gashasino.mobile.data.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository by lazy {
        val userDao = AppDatabase.getDatabase(application).userDao()
        UserRepository(userDao)
    }

    val userMonedas: StateFlow<Int?> = userRepository.userMonedas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null // Mantenemos null como valor inicial para manejar el estado de carga
        )

    // --- SOLUCIÓN: Añade este bloque init ---
    init {
        // Lanzamos una corrutina para realizar operaciones de base de datos en un hilo secundario.
        viewModelScope.launch(Dispatchers.IO) {
            // Comprobamos si ya existe algún usuario en la base de datos.
            val userExists = userRepository.userExists()
            if (!userExists) {
                // Si no existe ningún usuario, creamos uno nuevo.
                // Asumimos un nombre por defecto como "jugador1". Puedes cambiarlo o adaptarlo.
                val newUser = UserEntity(nombre = "jugador1", monedas = 1000)
                userRepository.insertUser(newUser)
            }
        }
    }

    fun addMonedas(cantidad: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addMonedas(cantidad)
        }
    }
}
