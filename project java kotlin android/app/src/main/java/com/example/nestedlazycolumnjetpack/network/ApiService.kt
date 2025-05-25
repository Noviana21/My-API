package com.example.nestedlazycolumnjetpack.network

import retrofit2.http.GET
import com.example.nestedlazycolumnjetpack.model.ImageItem

// ApiService mendefinisikan endpoint API
interface ApiService {
    @GET("/")
    suspend fun getImages(): List<ImageItem>
}