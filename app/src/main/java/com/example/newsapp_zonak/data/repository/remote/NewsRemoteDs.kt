package com.example.newsapp_zonak.data.repository.remote

import com.example.newsapp_zonak.data.models.NewsResponseDto
import com.example.newsapp_zonak.domain.repository.remote.INewsRemoteDs

class NewsRemoteDs (private val newsApiService: NewsApiService): INewsRemoteDs {
    override suspend fun getTopHeadlines(category: String): NewsResponseDto {
        return newsApiService.getTopHeadlines(category)
    }

}