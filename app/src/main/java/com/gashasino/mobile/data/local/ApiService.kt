package com.gashasino.mobile.data.local

import com.gashasino.mobile.model.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @POST("user")
    suspend fun register(@Body request: RegisterRequest): Response<UsuarioDto>

    // Esta es la llamada conflictiva.
    // Si el backend ignora el @Query, devolverá TODOS los usuarios.
    @GET("user")
    suspend fun findUserByEmail(@Query("email") correo: String): Response<List<UsuarioDto>>

    @GET("user/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<UsuarioDto>
}

