package com.gashasino.mobile.data


import com.gashasino.mobile.model.usuario
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}