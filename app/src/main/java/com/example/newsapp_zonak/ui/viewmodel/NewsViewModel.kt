package com.example.newsapp_zonak.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.common.ui.NewsViewModel
import com.example.newsapp_zonak.common.ui.ViewAction
import com.example.newsapp_zonak.domain.intractor.GetCachedTopHeadlinesUseCase
import com.example.newsapp_zonak.domain.intractor.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getCachedTopHeadlinesUseCase: GetCachedTopHeadlinesUseCase
) : NewsViewModel<NewsContract.NewsAction, NewsContract.NewsEvent, NewsContract.NewsState>(
    NewsContract.NewsState.initial()
) {


    private fun loadTopHeadlines(category: String) {
        viewModelScope.launch {
            // First, try to fetch data from the network
            getTopHeadlinesUseCase(category).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // If successful, update state with the fetched data
                        setState(viewState.value.copy(topHeadlines = result.data, isLoading = false))
                    }
                    is Resource.Error -> {
                        sendEvent(
                            NewsContract.NewsEvent.ShowError(
                                result.exception.message ?: "Unknown error"
                            )
                        )
                        // If an error occurs, attempt to fetch data from the cache
                        getCachedTopHeadlinesUseCase(category).collect { cachedResult ->
                            when (cachedResult) {
                                is Resource.Success -> {
                                    // If cached data is available, update state with the cached data
                                    setState(viewState.value.copy(topHeadlines = cachedResult.data, isLoading = false))
                                }
                                is Resource.Error -> {
                                    // If there's an error with cached data too, update state accordingly
                                    setState(viewState.value.copy(isLoading = false, exception = cachedResult.exception))
                                    sendEvent(
                                        NewsContract.NewsEvent.ShowError(
                                            cachedResult.exception.message ?: "Unknown error"
                                        )
                                    )
                                }
                                is Resource.Loading -> {
                                    // If loading cached data, update state with loading status
                                    setState(viewState.value.copy(isLoading = true))
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        // If loading data from the network, update state with loading status
                        setState(viewState.value.copy(isLoading = true))
                    }
                }
            }
        }
    }


    override fun clearState() {
        setState(NewsContract.NewsState.initial())
    }

    override fun onActionTrigger(action: ViewAction?) {
        setState(oldViewState.copy(action = action))
        when (action) {
            is NewsContract.NewsAction.SelectCategory -> loadTopHeadlines(action.category)
            is NewsContract.NewsAction.SelectArticle -> sendEvent(
                NewsContract.NewsEvent.NavigateToArticleDetails(
                    action.article
                )
            )

        }
    }
}
