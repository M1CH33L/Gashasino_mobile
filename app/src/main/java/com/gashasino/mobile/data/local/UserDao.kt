package com.gashasino.mobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Obtiene las monedas del primer usuario encontrado.
     * Devuelve un Flow que emite el valor cada vez que cambia.
     */
    @Query("SELECT monedas FROM user_profile LIMIT 1")
    fun getMonedas(): Flow<Int?>

    /**
     * Modifica las monedas del primer usuario encontrado.
     * Puede recibir un valor positivo (añadir) o negativo (restar).
     */
    @Query("UPDATE user_profile SET monedas = monedas + :cantidad WHERE id = (SELECT id FROM user_profile LIMIT 1)")
    suspend fun addMonedas(cantidad: Int)

    /**
     * Comprueba si existe al menos un usuario en la tabla.
     * Devuelve `true` si hay al menos una fila, `false` en caso contrario.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM user_profile LIMIT 1)")
    suspend fun userExists(): Boolean

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Si el usuario ya existe, lo reemplaza (gracias a OnConflictStrategy.REPLACE).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
}
