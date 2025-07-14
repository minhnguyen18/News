package com.example.news.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.news.R
import androidx.compose.ui.draw.drawBehind
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.filled.FavoriteBorder
import com.example.news.model.model.NewsArticle
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.navigation.NavHostController
import com.example.news.model.network.NYTApiService
import com.example.news.model.network.NewsApiClient
import com.example.news.model.network.NewsApiService
import com.example.news.model.repository.NYTRepository
import com.example.news.presenter.NYTPresenter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



@Composable
fun HomeScreen(viewModel: NewsViewModel = viewModel(),
               onReadMoreClick: (NewsArticle) -> Unit,
               onLogoutClick: () -> Unit) {
    val articles by viewModel.articles
    val latestArticles = articles.take(3)
    val featuredArticles = if (articles.size > 3) articles.drop(3) else emptyList()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("NEWS API", fontSize = 18.sp, fontWeight = FontWeight.Black)
                var expanded by remember { mutableStateOf(false) }
                Box{
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                expanded = false
                                onLogoutClick()
                            }
                        )
                    }
                }

            }
        }

        item {
            Text("Hey, Minh!", fontSize = 16.sp, color = Color.Gray)
            Text(
                "Find the Latest\nUpdates",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
        }

        item { FlatSearchBar() }

        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryIcon("Tech", R.drawable.tech_icon)
                CategoryIcon("Startups", R.drawable.startup_icon)
                CategoryIcon("Crypto", R.drawable.crypto_icon)
                CategoryIcon("Business", R.drawable.business_icon)
            }
        }
        // Swipe "Latest News"
        item {
            Text("Latest News", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0077CC))
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(latestArticles) { article ->
                    SwipeableNewsItem(
                        title = article.title,
                        imageUrl = article.urlToImage,
                        author = article.author ?: "Unknown",
                        timeAgo = article.publishedAtRelative ?: "5 hours ago",
                        onClick = { onReadMoreClick(article)}
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Text("Featured Stories", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF0077CC))
        }

        if (articles.isEmpty()) {
            item {
                Text("⚠ No news available or API failed.", color = Color.Red)
            }
        } else {
            items(featuredArticles) { article ->
                NewsItem(article.title, article.urlToImage, onClick = {onReadMoreClick(article)})
            }
        }
    }
}

@Composable
fun FlatSearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Search for News",
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Gray,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun CategoryIcon(label: String, iconRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = Color.LightGray,
            modifier = Modifier.size(56.dp),
            tonalElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(28.dp),
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp)
    }
}
@Composable
fun SwipeableNewsItem(title: String, imageUrl: String?, author: String, timeAgo: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(280.dp)
            .height(220.dp)
            .clickable { onClick()}
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                   // Text(text = author, color = Color.White, fontSize = 10.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = timeAgo, color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun NewsItem(title: String, imageUrl: String?, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Surface(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = Color.Gray
            ) {}
        }

        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                "Read news",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { onClick() }
            )
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: NewsViewModel,
    nytViewModel: NYTViewModel
) {
    val bottomNavController = rememberNavController()
    val navItems = listOf("home", "pages", "sports")
    val apiKey = "vPW25PAIVsZPBx55l3F0wGPUiXEX88LZ"

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                navItems.forEach { route ->
                    NavigationBarItem(
                        icon = {
                            when (route) {
                                "home" -> Icon(Icons.Default.Home, contentDescription = null)
                                "pages" -> Icon(Icons.Default.Star, contentDescription = null)
                                "sports" -> Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                            }
                        },
                        label = { Text(route.replaceFirstChar { it.uppercaseChar() }) },
                        selected = currentRoute == route,
                        onClick = {
                            bottomNavController.navigate(route) {
                                popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = viewModel,
                    onReadMoreClick = { article ->
                        viewModel.selectArticle(article)
                        navController.navigate("detail")
                    },
                    onLogoutClick = {
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                )
            }
            composable("pages") {
                PagesScreen(viewModel = nytViewModel,
                    onReadMoreClick = { article ->
                        nytViewModel.selectArticle(article)
                        navController.navigate("nyt_detail")
                    }
                )
            }
            composable("sports") {
                SportScreen(
                    apiKey = "7d760c25a4b74092a053a8262f2b48a4",
                    onReadMoreClick = { /* handle read more if needed */ }
                )
            }
        }
    }
}
@Composable
fun PagesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Pages Screen", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
