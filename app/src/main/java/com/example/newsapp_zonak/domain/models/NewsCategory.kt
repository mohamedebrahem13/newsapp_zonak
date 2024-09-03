package com.example.newsapp_zonak.domain.models

sealed class NewsCategory(val name: String) {
    data object Business : NewsCategory("business")
    data object Entertainment : NewsCategory("entertainment")
    data object General : NewsCategory("general")
    data object Health : NewsCategory("health")
    data object Science : NewsCategory("science")
    data object Sports : NewsCategory("sports")
    data object Technology : NewsCategory("technology")

    companion object {
        val allCategories =
            listOf(Business, Entertainment, General, Health, Science, Sports, Technology)
    }
}