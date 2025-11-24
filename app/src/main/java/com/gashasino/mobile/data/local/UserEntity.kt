package com.gashasino.mobile.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val monedas: Int = 1000
)