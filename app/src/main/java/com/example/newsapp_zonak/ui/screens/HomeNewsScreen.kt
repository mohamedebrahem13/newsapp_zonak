package com.example.newsapp_zonak.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp_zonak.domain.models.NewsCategory
import com.example.newsapp_zonak.ui.items.CategoryItem
import com.example.newsapp_zonak.ui.items.NewsItem
import com.example.newsapp_zonak.ui.theme.Newsapp_zonakTheme
import com.example.newsapp_zonak.ui.viewmodel.NewsContract
import com.example.newsapp_zonak.ui.viewmodel.NewsViewModel


@Composable
fun HomeNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val newsState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    var selectedCategory by remember {
        mutableStateOf(NewsCategory.allCategories.firstOrNull())
    }

    // Handle events such as errors
    LaunchedEffect(viewModel.singleEvent) {
        viewModel.singleEvent.collect { event ->
            when (event) {
                is NewsContract.NewsEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is NewsContract.NewsEvent.NavigateToArticleDetails -> {
                    navController.navigate("article_details/${Uri.encode(event.article.url)}")
                }
            }
        }
    }

    // Handle category selection when the category changes
    selectedCategory?.let { category ->
        LaunchedEffect(category) {
            viewModel.onActionTrigger(NewsContract.NewsAction.SelectCategory(category.name))
        }
    }

    Column(modifier) {
        // Category list
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(items = NewsCategory.allCategories) { category ->
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

        // Show progress bar while loading
        if (newsState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .semantics { contentDescription = "Loading content" } // Add semantics here
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .testTag("news_list")
                    .semantics { contentDescription = "news_list" } , // Semantics for the list
                contentPadding = PaddingValues(16.dp) // Add padding to the content// Semantics for the list
            ) {
                items(newsState.topHeadlines) { article ->
                    NewsItem(
                        article = article,
                        onClick = {
                            viewModel.onActionTrigger(NewsContract.NewsAction.SelectArticle(article))
                        }
                    )
                }
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