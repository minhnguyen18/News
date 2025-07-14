package com.example.news.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.news.model.model.NewsArticle
import androidx.compose.ui.Alignment
import com.example.news.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.news.presenter.SportPresenter
import com.example.news.presenter.SportPresenterImpl
import com.example.news.presenter.SportView
import com.example.news.model.repository.SportRepository
import com.example.news.model.network.NewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * SportScreen displays sports news articles in a list, similar to PagesScreen.
 * Follows view layer and navigation instructions for composable structure and navigation.
 */
@Composable
fun SportScreen(
    apiKey: String,
    onReadMoreClick: (NewsArticle) -> Unit
) {
    val context = LocalContext.current
    val sportRepository = remember {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(NewsApiService::class.java)
        SportRepository(apiService)
    }
    val presenter = remember { SportPresenterImpl(sportRepository) }
    var articles by remember { mutableStateOf<List<NewsArticle>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        val view = object : SportView {
            override fun showSportsNews(news: List<NewsArticle>) {
                articles = news
            }
            override fun showError(message: String) {
                error = message
            }
        }
        presenter.attachView(view)
        presenter.loadSportsNews(apiKey)
        onDispose { presenter.detachView() }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                // Background image for sports
                Image(
                    painter = painterResource(id = R.drawable.sports_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Title
                Text(
                    text = "SPORT NEWS",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 50.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
        if (error != null) {
            item {
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        } else if (articles.isEmpty()) {
            item {
                Text(
                    "No sports news available. Please check your API key or connection.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            items(articles) { article ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    NewsItem(
                        title = article.title,
                        imageUrl = article.urlToImage,
                        onClick = { onReadMoreClick(article) }
                    )
                }
            }
        }
    }
}
