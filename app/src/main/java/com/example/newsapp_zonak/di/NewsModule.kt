package com.example.newsapp_zonak.di

import com.example.newsapp_zonak.data.repository.NewsRepository
import com.example.newsapp_zonak.data.repository.local.room.ArticleDao
import com.example.newsapp_zonak.data.repository.local.room.NewsLocalDs
import com.example.newsapp_zonak.data.repository.remote.NewsApiService
import com.example.newsapp_zonak.data.repository.remote.NewsRemoteDs
import com.example.newsapp_zonak.domain.intractor.GetCachedTopHeadlinesUseCase
import com.example.newsapp_zonak.domain.intractor.GetTopHeadlinesUseCase
import com.example.newsapp_zonak.domain.repository.INewsRepository
import com.example.newsapp_zonak.domain.repository.local.room.INewsLocalDs
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
    fun provideNewsLocalDs(articleDao: ArticleDao): INewsLocalDs {
        return NewsLocalDs(articleDao)
    }

    @Provides
    fun provideNewsRepository(
        newsRemoteDs: INewsRemoteDs,
        newsLocalDs: INewsLocalDs
    ): INewsRepository {
        return NewsRepository(newsRemoteDs, newsLocalDs)
    }
    @Provides
    fun provideGetTopHeadlinesUseCase(newsRepository: INewsRepository): GetTopHeadlinesUseCase {
        return GetTopHeadlinesUseCase(newsRepository)
    }
    @Provides
    fun provideGetCachedTopHeadlinesUseCase(newsRepository: INewsRepository): GetCachedTopHeadlinesUseCase {
        return GetCachedTopHeadlinesUseCase(newsRepository)
    }

}