package com.example.newsapp_zonak.ui

import com.example.newsapp_zonak.common.ui.ViewAction
import com.example.newsapp_zonak.common.ui.ViewEvent
import com.example.newsapp_zonak.common.ui.ViewState
import com.example.newsapp_zonak.domain.models.Article

interface NewsContract {

    sealed class NewsAction : ViewAction {
        data class SelectCategory(val category: String) : NewsAction()
        data class SelectArticle(val article: Article) : NewsAction()
    }

    sealed class NewsEvent : ViewEvent {
        data class NavigateToArticleDetails(val article: Article) : NewsEvent()
        data class ShowError(val message: String) : NewsEvent()
    }

    data class NewsState(
        val isLoading: Boolean = false,
        val categories: List<String> = emptyList(),
        val topHeadlines: List<Article> = emptyList(),
        val selectedCategory: String = "",
        val selectedArticle: Article? = null,
        val exception: Exception? = null,
        val action: ViewAction?=null
    ) : ViewState {
        companion object {
            fun initial() = NewsState()
        }
    }
}