package com.example.news.view.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.model.model.NYTArticle
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import com.example.news.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Text

@Composable
fun PagesScreen(viewModel: NYTViewModel, onReadMoreClick: (NYTArticle) -> Unit) {
    val articles by viewModel.articles.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                // Background image
                Image(
                    painter = painterResource(id = R.drawable.nyt_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // NYT title
                Text(
                    text = "THE NEW\nYORK TIMES",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 50.sp,
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }



        if (articles.isEmpty()) {
            item {
                Text(
                    "No articles available. Please check your API key or connection.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            items(articles) { article ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    NewsItem(
                        title = article.title,
                        imageUrl = article.imageUrl,
                        onClick = {
                            println("Clicked on article: ${article.title}")
                            onReadMoreClick(article)
                        }
                    )
                }
            }

        }
    }
}

