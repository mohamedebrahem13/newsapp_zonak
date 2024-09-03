package com.example.newsapp_zonak.domain.intractor

import app.cash.turbine.test
import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.data.repository.NewsRepository
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.models.Source
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
class GetTopHeadlinesUseCaseTest {

    private val newsRepository = mockk<NewsRepository>()
    private val getTopHeadlinesUseCase = GetTopHeadlinesUseCase(newsRepository)

    // Sample Source object
    private val source = Source(id = "1", name = "SourceName")

    // Sample Article object
    private val article = Article(
        source = source,
        author = "AuthorName",
        title = "Article Title",
        description = "Article Description",
        url = "https://example.com",
        imageUrl = "https://example.com/image.jpg",
        publishedAt = "2024-09-04T00:00:00Z",
        content = "Article Content"
    )

    @Test
    fun `invoke getTopHeadlines should emit loading then success when repository returns data`() = runTest {
        // Arrange
        val category = "Technology"
        val articles = listOf(article)
        coEvery { newsRepository.getTopHeadlines(category) } returns articles

        // Act & Assert
        getTopHeadlinesUseCase(category).test {
            assertEquals(Resource.loading<List<Article>>(loading = true), awaitItem())
            assertEquals(Resource.success(articles), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke getTopHeadlines should emit loading then error when repository throws exception`() = runTest {
        // Arrange
        val category = "Technology"
        val exception = Exception("Network error")
        coEvery { newsRepository.getTopHeadlines(category) } throws exception

        // Act & Assert
        getTopHeadlinesUseCase(category).test {
            assertEquals(Resource.loading<List<Article>>(loading = true), awaitItem())
            assertEquals(Resource.error<List<Article>>(exception), awaitItem())
            awaitComplete()
        }
    }
}