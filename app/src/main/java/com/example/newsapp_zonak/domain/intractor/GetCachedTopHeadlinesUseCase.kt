package com.example.newsapp_zonak.domain.intractor

import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCachedTopHeadlinesUseCase(
    private val newsRepository: INewsRepository
) {

    operator fun invoke(category: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.loading(loading = true))
        try {
            // Fetch the cached articles from the local database
            val cachedArticles = newsRepository.getLocalTopHeadlinesByCategory(category)
            emit(Resource.success(cachedArticles))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}