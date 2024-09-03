package com.example.newsapp_zonak.domain.repository.local.room

import com.example.newsapp_zonak.data.repository.local.room.models.ArticleEntity

interface INewsLocalDs {
    suspend fun saveArticles(articles: List<ArticleEntity>, category: String)
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    suspend fun getArticlesByCategory(category: String): List<ArticleEntity>
    suspend fun clearAll()
}