package com.example.newsapp_zonak.data.repository

import com.example.newsapp_zonak.data.mapper.ArticleMapper
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.repository.INewsRepository
import com.example.newsapp_zonak.domain.repository.local.room.INewsLocalDs
import com.example.newsapp_zonak.domain.repository.remote.INewsRemoteDs
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsRemoteDs: INewsRemoteDs,
                                         private val newsLocalDs: INewsLocalDs): INewsRepository {
    override suspend fun getTopHeadlines(category: String): List<Article> {
        val responseDto = newsRemoteDs.getTopHeadlines(category)
        val articles = ArticleMapper.dtoListToDomain(responseDto.articles)

        // Save the articles with the category
        val articleEntities = ArticleMapper.domainListToEntity(articles, category)
        newsLocalDs.saveArticles(articleEntities, category)

        return articles
    }
    override suspend fun getLocalTopHeadlinesByCategory(category: String): List<Article> {
        // Retrieve the articles from the local database by category
        val articleEntities = newsLocalDs.getArticlesByCategory(category)
        return ArticleMapper.entityListToDomain(articleEntities)
    }

}