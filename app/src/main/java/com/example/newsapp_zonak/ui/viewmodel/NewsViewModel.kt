package com.example.newsapp_zonak.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.newsapp_zonak.common.Resource
import com.example.newsapp_zonak.common.ui.NewsViewModel
import com.example.newsapp_zonak.common.ui.ViewAction
import com.example.newsapp_zonak.domain.intractor.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
) : NewsViewModel<NewsContract.NewsAction, NewsContract.NewsEvent, NewsContract.NewsState>(
    NewsContract.NewsState.initial()
) {


    private fun loadTopHeadlines(category: String) {
        viewModelScope.launch {
            getTopHeadlinesUseCase.execute(category)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> setState(viewState.value.copy(topHeadlines = result.data, isLoading = false))
                        is Resource.Error -> {
                            setState(viewState.value.copy(isLoading = false, exception = result.exception))
                            sendEvent(
                                NewsContract.NewsEvent.ShowError(
                                    result.exception.message ?: "Unknown error"
                                )
                            )
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
            is NewsContract.NewsAction.SelectArticle -> sendEvent(
                NewsContract.NewsEvent.NavigateToArticleDetails(
                    action.article
                )
            )

        }
    }
}
