package com.example.newsapp_zonak.di

import com.example.newsapp_zonak.data.repository.NewsRepository
import com.example.newsapp_zonak.data.repository.remote.NewsApiService
import com.example.newsapp_zonak.data.repository.remote.NewsRemoteDs
import com.example.newsapp_zonak.domain.intractor.GetTopHeadlinesUseCase
import com.example.newsapp_zonak.domain.repository.INewsRepository
import com.example.newsapp_zonak.domain.repository.remote.INewsRemoteDs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NewsModule {

    @Provides
    fun provideNewsRemoteDs(newsApiService: NewsApiService): INewsRemoteDs {
        return NewsRemoteDs(newsApiService)
    }

    @Provides
    fun provideNewsRepository(newsRemoteDs: NewsRemoteDs): INewsRepository {
        return NewsRepository(newsRemoteDs)
    }
    @Provides
    fun provideGetTopHeadlinesUseCase(newsRepository: INewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(newsRepository)
    }


}