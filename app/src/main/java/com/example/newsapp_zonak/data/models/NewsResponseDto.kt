package com.example.newsapp_zonak.data.models

data class NewsResponseDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleDto>?
)