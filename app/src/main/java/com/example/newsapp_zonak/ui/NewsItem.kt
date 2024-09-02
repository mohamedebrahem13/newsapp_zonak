package com.example.newsapp_zonak.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp_zonak.domain.models.Article

@Composable
fun NewsItem(
    article: Article,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            article.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUrl
                    ),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(
                text = article.title ?: "No Title",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description ?: "No Description",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
