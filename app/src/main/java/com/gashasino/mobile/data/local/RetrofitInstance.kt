package com.gashasino.mobile.data.local

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Aumentamos el tiempo de conexión
        .readTimeout(30, TimeUnit.SECONDS)    // Aumentamos el tiempo de lectura
        .writeTimeout(30, TimeUnit.SECONDS)   // Aumentamos el tiempo de escritura
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            // URL base corregida (sin el endpoint /user)
            .baseUrl("https://x8ki-letl-twmt.n7.xano.io/api:43H4O7vE/") 
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
