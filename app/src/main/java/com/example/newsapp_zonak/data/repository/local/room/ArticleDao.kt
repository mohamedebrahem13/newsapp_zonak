package com.example.newsapp_zonak.data.repository.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp_zonak.data.repository.local.room.models.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM article WHERE url = :url")
    suspend fun getArticleByUrl(url: String): ArticleEntity?

    @Query("SELECT * FROM article WHERE category = :category")
    suspend fun getArticlesByCategory(category: String): List<ArticleEntity> // New method

    @Query("DELETE FROM article")
    suspend fun clearAll()

    @Query("DELETE FROM article WHERE category = :category")
    suspend fun clearArticlesByCategory(category: String)
}