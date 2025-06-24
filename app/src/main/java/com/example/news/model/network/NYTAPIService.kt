package com.example.news.model.network

import com.example.news.model.model.NYTResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApiService {
    @GET("topstories/v2/home.json")
    suspend fun getTopStories(@Query("api-key") apiKey: String): NYTResponse
}
