package com.example.nestedlazycolumnjetpack.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ApiClient membuat instance Retrofit dan menghasilkan apiService
object ApiClient {
    private const val BASE_URL = "https://aventurine.free.beeceptor.com"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}