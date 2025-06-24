package com.example.news.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NYTApiClient {
    private const val BASE_URL = "https://api.nytimes.com/svc/"

    val apiService: NYTApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYTApiService::class.java)
    }
}
