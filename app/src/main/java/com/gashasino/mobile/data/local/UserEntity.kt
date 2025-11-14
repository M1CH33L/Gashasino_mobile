package com.gashasino.mobile.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
class UserEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val nombre: String = "michel o luciano q se yo",
    val correo: String = "vivaelfortnite@fortnite.fn",
    val edad: Int = 18,
    val contrasena: String = "123",
    val monedas: Int = 1000
)