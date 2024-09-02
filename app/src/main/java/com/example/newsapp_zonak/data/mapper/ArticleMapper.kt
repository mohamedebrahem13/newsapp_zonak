package com.example.newsapp_zonak.data.mapper

import com.example.newsapp_zonak.common.Mapper
import com.example.newsapp_zonak.data.models.ArticleDto
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.models.Source

object ArticleMapper: Mapper<ArticleDto, Article, Unit>() {

    override fun dtoToDomain(model: ArticleDto): Article {
        return Article(
            source = Source(
                id = model.source?.id.orEmpty(),
                name = model.source?.name.orEmpty()
            ),
            author = model.author.orEmpty(),
            title = model.title.orEmpty(),
            description = model.description.orEmpty(),
            url = model.url.orEmpty(),
            imageUrl = model.urlToImage.orEmpty(),
            publishedAt = model.publishedAt.orEmpty(),
            content = model.content.orEmpty()
        )
    }
    // Add a method to map a list of ArticleDto to a list of Article
    fun dtoListToDomain(list: List<ArticleDto>?): List<Article> {
        return list?.map { dtoToDomain(it) } ?: emptyList()
    }
}