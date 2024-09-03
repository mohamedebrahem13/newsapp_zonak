package com.example.newsapp_zonak.data.mapper

import com.example.newsapp_zonak.common.Mapper
import com.example.newsapp_zonak.data.models.ArticleDto
import com.example.newsapp_zonak.data.repository.local.room.models.ArticleEntity
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.models.Source

object ArticleMapper: Mapper<ArticleDto, Article, ArticleEntity>() {

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

    override fun entityToDomain(model: ArticleEntity): Article {
        return Article(
            source = Source(
                id = model.sourceId,
                name = model.sourceName
            ),
            author = model.author,
            title = model.title,
            description = model.description,
            url = model.url,
            imageUrl = model.imageUrl,
            publishedAt = model.publishedAt,
            content = model.content
        )
    }

    // Update to include category when mapping from domain to entity
    override fun domainToEntity(model: Article): ArticleEntity {
        return ArticleEntity(
            url = model.url,
            sourceId = model.source.id,
            sourceName = model.source.name,
            author = model.author,
            title = model.title,
            description = model.description,
            imageUrl = model.imageUrl,
            publishedAt = model.publishedAt,
            content = model.content,
            category = "" // This will be provided by the domainListToEntity method below
        )
    }

    // Map a list of Articles to a list of ArticleEntities, including the category
    fun domainListToEntity(list: List<Article>, category: String): List<ArticleEntity> {
        return list.map { article ->
            domainToEntity(article).copy(category = category)
        }
    }

    fun entityListToDomain(list: List<ArticleEntity>): List<Article> {
        return list.map { entityToDomain(it) }
    }

    fun dtoListToDomain(list: List<ArticleDto>?): List<Article> {
        return list?.map { dtoToDomain(it) } ?: emptyList()
    }
}