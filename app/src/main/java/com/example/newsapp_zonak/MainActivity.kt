package com.example.newsapp_zonak

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp_zonak.ui.screens.ArticleDetailsScreen
import com.example.newsapp_zonak.ui.screens.HomeNewsScreen
import com.example.newsapp_zonak.ui.viewmodel.NewsViewModel
import com.example.newsapp_zonak.ui.theme.Newsapp_zonakTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Newsapp_zonakTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
    @Composable
    fun AppNavHost(navController: NavHostController, modifier: Modifier) {
        val viewModel: NewsViewModel = hiltViewModel()
        val newsState = viewModel.viewState.collectAsState().value

        NavHost(navController = navController, startDestination = "home_news") {
            composable("home_news") {
                HomeNewsScreen(
                    viewModel = viewModel,
                    navController = navController,
                    modifier = modifier
                )
            }
            composable("article_details/{articleUrl}") { backStackEntry ->
                val encodedArticleUrl = backStackEntry.arguments?.getString("articleUrl") ?: return@composable
                val articleUrl = Uri.decode(encodedArticleUrl)
                val article = newsState.topHeadlines.find { it.url == articleUrl }
                if (article != null) {
                    ArticleDetailsScreen(
                        article = article,
                        navController = navController,
                    )
                }
            }
        }
    }
}

