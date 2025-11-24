package com.gashasino.mobile.data.local

import com.gashasino.mobile.model.UsuarioDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    
    @FormUrlEncoded
    @POST("user")
    suspend fun register(
        @Field("name") nombre: String,
        @Field("email") correo: String,
        @Field("password") contrasena: String,
        @Field("edad") edad: Int
    ): Response<UsuarioDto>

    @GET("user")
    suspend fun findUserByEmail(@Query("email") correo: String): Response<List<UsuarioDto>>

    @GET("user/{id}")
    suspend fun getUsuario(@retrofit2.http.Path("id") id: Int): Response<UsuarioDto>
}
