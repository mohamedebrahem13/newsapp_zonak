package com.example.newsapp_zonak.domain.intractor

import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopHeadlinesUseCase (
    private val newsRepository: INewsRepository
) {

    fun execute(category: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.loading(loading = true))
        try {
            val response = newsRepository.getTopHeadlines(category)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}