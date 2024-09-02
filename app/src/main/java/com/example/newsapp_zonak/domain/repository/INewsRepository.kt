package com.example.newsapp_zonak.domain.repository

import com.example.newsapp_zonak.domain.models.Article

interface INewsRepository{
    suspend fun getTopHeadlines(category: String):List<Article>
    suspend fun getAvailableCategories(): List<String>

}