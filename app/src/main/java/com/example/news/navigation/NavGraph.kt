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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.model.model.BaseArticle
import com.example.news.view.home.MainScreen
import com.example.news.view.home.NYTViewModel
import com.example.news.view.home.NYTViewModelFactory
import com.example.news.model.network.NYTApiClient
import com.example.news.model.repository.NYTRepository
import com.example.news.presenter.NYTPresenter
import com.example.news.view.home.PagesScreen


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
    object Pages : Screen("pages")
    object Detail : Screen("detail")
    object NYTDetail : Screen("nyt_detail")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val apiKey = "vPW25PAIVsZPBx55l3F0wGPUiXEX88LZ"
    val nytApiService = NYTApiClient.apiService
    val nytRepository = NYTRepository(nytApiService)
    val nytPresenter = NYTPresenter(nytRepository)
    val nytViewModel: NYTViewModel = viewModel(
        factory = NYTViewModelFactory(
            presenter = NYTPresenter(NYTRepository(NYTApiClient.apiService)),
            apiKey = apiKey
        )
    )

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Main.route) {
            MainScreen(navController = navController, viewModel = viewModel, nytViewModel = nytViewModel )
        }

        composable(Screen.Detail.route) {
            val article = viewModel.selectedArticle.value
            if (article != null) {
                NewsDetailScreen(article = article, navController = navController)
            } else {
                Text("No article selected.")
            }
        }
        composable(Screen.Pages.route) {
            PagesScreen(
                viewModel = nytViewModel,
                onReadMoreClick = { article ->
                    nytViewModel.selectArticle(article)
                    navController.navigate(Screen.NYTDetail.route)
                }
            )
        }

        composable(Screen.NYTDetail.route) {
            val article = nytViewModel.selectedArticle.collectAsState().value
            println("Navigating to NYTDetail with: ${article?.title}")
            if (article != null) {
                NewsDetailScreen(article = article, navController = navController)
            } else {
                Text("No article selected.")
            }
        }



    }
}


