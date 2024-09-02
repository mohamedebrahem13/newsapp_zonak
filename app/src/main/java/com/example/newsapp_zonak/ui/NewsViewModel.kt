package com.example.newsapp_zonak.ui

import androidx.lifecycle.viewModelScope
import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.common.ui.NewsViewModel
import com.example.newsapp_zonak.common.ui.ViewAction
import com.example.newsapp_zonak.domain.intractor.GetAvailableCategoriesUseCase
import com.example.newsapp_zonak.domain.intractor.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase
) : NewsViewModel<NewsContract.NewsAction, NewsContract.NewsEvent, NewsContract.NewsState>(
    NewsContract.NewsState.initial()) {

    init {
        loadAvailableCategories()
    }

    private fun loadTopHeadlines(category: String) {
        viewModelScope.launch {
            getTopHeadlinesUseCase.execute(category)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> setState(viewState.value.copy(topHeadlines = result.data, isLoading = false))
                        is Resource.Error -> {
                            setState(viewState.value.copy(isLoading = false, exception = result.exception))
                            sendEvent(NewsContract.NewsEvent.ShowError(result.exception.message ?: "Unknown error"))
                        }
                        is Resource.Loading -> setState(viewState.value.copy(isLoading = true))
                    }
                }
        }
    }

    private fun loadAvailableCategories() {
        viewModelScope.launch {
            getAvailableCategoriesUseCase.execute()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setState(viewState.value.copy(categories = result.data, isLoading = false))
                            if (result.data.isNotEmpty()) {
                                processIntent(NewsContract.NewsAction.SelectCategory(result.data.first()))
                            }
                        }
                        is Resource.Error -> {
                            setState(viewState.value.copy(isLoading = false, exception = result.exception))
                            sendEvent(NewsContract.NewsEvent.ShowError(result.exception.message ?: "Unknown error"))
                        }
                        is Resource.Loading -> setState(viewState.value.copy(isLoading = true))
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
            is NewsContract.NewsAction.SelectArticle -> sendEvent(NewsContract.NewsEvent.NavigateToArticleDetails(action.article))

        }
    }
}
