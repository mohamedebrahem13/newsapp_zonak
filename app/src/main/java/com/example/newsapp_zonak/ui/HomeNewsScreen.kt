package com.example.newsapp_zonak.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp_zonak.ui.theme.Newsapp_zonakTheme


@Composable
fun HomeNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    navController: NavController,

) {
    val newsState by viewModel.viewState.collectAsState()
    var selectedCategory by remember {
        mutableStateOf(newsState.categories.firstOrNull())
    }

    // Handle category selection when the category changes
    selectedCategory?.let { category ->
        LaunchedEffect(category) {
            viewModel.onActionTrigger(NewsContract.NewsAction.SelectCategory(category))
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        // Category list
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items = newsState.categories) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category == selectedCategory,
                    onClick = {
                        selectedCategory = category
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // News list
        LazyColumn {
            items(newsState.topHeadlines) { article ->
                NewsItem(
                    article = article,
                    onClick = {
                        viewModel.onActionTrigger(NewsContract.NewsAction.SelectArticle(article))
                        navController.navigate("article_details/${article.url}")
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewHomeNewsScreen() {
    Newsapp_zonakTheme {
        HomeNewsScreen(
            viewModel = hiltViewModel(),
            navController = rememberNavController()
        )
    }
}