package com.example.newsapp_zonak.domain.models

data class Article (
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?
)