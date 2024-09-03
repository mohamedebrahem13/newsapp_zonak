package com.example.newsapp_zonak.domain.repository.remote

import com.example.newsapp_zonak.data.models.NewsResponseDto

interface INewsRemoteDs {
    suspend fun getTopHeadlines(category: String): NewsResponseDto
}