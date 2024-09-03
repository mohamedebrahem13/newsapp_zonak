package com.example.newsapp_zonak.data.repository.local.room

import com.example.newsapp_zonak.data.repository.local.room.models.ArticleEntity
import com.example.newsapp_zonak.domain.repository.local.room.INewsLocalDs

class NewsLocalDs(private val articleDao: ArticleDao): INewsLocalDs {

    override suspend fun saveArticles(articles: List<ArticleEntity>, category: String) {
        // Clear existing articles for the category
        articleDao.clearArticlesByCategory(category)
        // Include category while saving articles
        val articlesWithCategory = articles.map { it.copy(category = category) }
        articleDao.insertArticles(articlesWithCategory)
    }
    override suspend fun getArticleByUrl(url: String): ArticleEntity? {
        return articleDao.getArticleByUrl(url)
    }
    override suspend fun getArticlesByCategory(category: String): List<ArticleEntity> {
        return articleDao.getArticlesByCategory(category) // New method implementation
    }

    override suspend fun clearAll() {
        articleDao.clearAll()

    }

}