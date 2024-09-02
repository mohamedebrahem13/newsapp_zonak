package com.example.newsapp_zonak.domain.intractor

import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.domain.repository.INewsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow


class GetAvailableCategoriesUseCase(private val newsRepository: INewsRepository) {
    fun execute(): Flow<Resource<List<String>>> = flow {
        emit(Resource.loading(loading = true))
        try {
            val categories = newsRepository.getAvailableCategories()
            emit(Resource.success(categories))
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }
}