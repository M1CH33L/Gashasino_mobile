package com.gashasino.mobile.data.local

import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que gestiona el acceso a los datos del usuario.
 * Abstrae las fuentes de datos (en este caso, solo el DAO de Room)
 * para el resto de la aplicación.
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Expone el Flow de monedas directamente desde el DAO.
     */
    val userMonedas: Flow<Int?> = userDao.getMonedas()

    /**
     * Delega la función de añadir/restar monedas al DAO.
     */
    suspend fun addMonedas(cantidad: Int) {
        userDao.addMonedas(cantidad)
    }

    /**
     * Delega la función de comprobar si existe un usuario al DAO.
     */
    suspend fun userExists(): Boolean {
        return userDao.userExists()
    }

    /**
     * Delega la función de insertar un nuevo usuario al DAO.
     */
    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }
}
