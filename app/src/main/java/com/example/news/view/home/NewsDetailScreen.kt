package com.example.news.view.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.news.model.model.NewsArticle
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.news.model.model.BaseArticle

@Composable
fun NewsDetailScreen(
    article: BaseArticle,
    navController: NavController
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        //Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Icon(Icons.Default.Person, contentDescription = "Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Author + Date
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = Color.Gray,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = article.author?.firstOrNull()?.uppercase() ?: "M",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = article.publishedAt?.let {
                        try {
                            val inputFormat =
                                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                            val date = inputFormat.parse(it)
                            outputFormat.format(date)
                        } catch (e: Exception) {
                            "Unknown date"
                        }
                    } ?: "Unknown date",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text("News", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Title
        Text(
            text = article.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        /// Show image
        if (!article.urlToImage.isNullOrBlank()) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Show description first
        article.description?.let {
            Text(
                text = it,
                fontSize = 15.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Show content
        val fullContent = article.content?.substringBefore("[+")?.trim()
        Text(
            text = fullContent ?: "No full article content available.",
            fontSize = 12.sp,
            color = Color.Gray
        )

        article.url?.let { url ->
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }) {
                Text("Read Full Article")
            }
        }
    }
}



