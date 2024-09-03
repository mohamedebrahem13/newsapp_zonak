package com.example.newsapp_zonak.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp_zonak.domain.models.Article
import com.example.newsapp_zonak.domain.models.Source

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(
    article: Article,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp) // Apply internal padding
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        // TopAppBar with Back Icon
        TopAppBar(
            title = { Text("Article Details") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Article Image
        article.imageUrl?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp), // Space below the image
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Article Title
        Text(
            text = article.title ?: "No Title",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Article Publication Date
        Text(
            text = article.publishedAt ?: "No Publication Date",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Article Description
        Text(
            text = article.description ?: "No Description",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Article Content
        Text(
            text = article.content ?: "No Content Available",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ArticleDetailsScreenPreview() {
    ArticleDetailsScreen(
        article = Article(
            source = Source(id = "1", name = "Source Name"),
            author = "Author Name",
            title = "Sample Article Title",
            description = "Sample article description.",
            url = "http://example.com",
            imageUrl = "http://example.com/image.jpg",
            publishedAt = "2024-09-01T12:00:00Z",
            content = "Sample article content."
        ),
        navController = rememberNavController()
    )
}