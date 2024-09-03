package com.example.newsapp_zonak.data.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp_zonak.data.repository.local.room.models.ArticleEntity

@Database(
    entities = [ArticleEntity::class], // Include all entities here
    version = 1, // Increment the version number if you change the schema
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Provide access to ArticleDao
    abstract fun articleDao(): ArticleDao

}