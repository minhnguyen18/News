package com.example.news.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.news.view.login.LoginScreen
import com.example.news.view.home.HomeScreen
import com.example.news.view.detail.NewsDetailScreen
import com.example.news.view.home.NewsViewModel
import androidx.compose.material3.Text


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Detail : Screen("detail")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onReadMoreClick = { article ->
                    viewModel.selectArticle(article)
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(Screen.Detail.route) {
            val article = viewModel.selectedArticle.value
            if (article != null) {
                NewsDetailScreen(article = article, navController = navController)
            } else {
                Text("No article selected.")
            }
        }
    }
}

