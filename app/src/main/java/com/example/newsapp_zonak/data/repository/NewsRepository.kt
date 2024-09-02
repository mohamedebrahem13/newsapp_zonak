package com.example.newsapp_zonak.data.repository

import com.example.newsapp_zonak.data.mapper.ArticleMapper
import com.example.newsapp_zonak.data.repository.remote.NewsRemoteDs
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.repository.INewsRepository
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRemoteDs: NewsRemoteDs): INewsRepository {
    override suspend fun getTopHeadlines(category: String): List<Article> {
        val responseDto = newsRemoteDs.getTopHeadlines(category)
        // Map the list of ArticleDto to a list of Article
        return ArticleMapper.dtoListToDomain(responseDto.articles)
    }

    override suspend fun getAvailableCategories(): List<String> {
        return newsRemoteDs.getAvailableCategories()
    }
}