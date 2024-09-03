package com.example.newsapp_zonak.data.repository.local.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val sourceId: String,
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val publishedAt: String,
    val content: String,
    val category: String // New category field
)