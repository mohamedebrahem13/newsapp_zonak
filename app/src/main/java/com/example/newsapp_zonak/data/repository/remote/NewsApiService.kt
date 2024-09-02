package com.example.newsapp_zonak.data.repository.remote

import com.example.newsapp_zonak.data.models.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {

    @GET("top-headlines/category/{category}/in.json")
    suspend fun getTopHeadlines(
        @Path("category") category: String
    ): NewsResponseDto

    @GET("available-categories.json")
    suspend fun getAvailableCategories(): List<String>
}