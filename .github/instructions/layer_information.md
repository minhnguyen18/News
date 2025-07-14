# Project Folder (Layer) Information

This document lists all main folders in the project, grouped by their typical architectural or functional layer in an Android project. The actual folders found in your codebase are included for clarity, with a breakdown of the main files and their functions.

---

## 1. Presentation Layer
- `app/src/main/java/com/example/news/ui/`
    - `theme/Color.kt`: Defines color schemes for the app's UI.
    - `theme/Theme.kt`: Sets up the overall theme for Jetpack Compose.
    - `theme/Type.kt`: Specifies typography styles for the UI.
- `app/src/main/java/com/example/news/view/`
    - `home/`
        - `HomeScreen.kt`: Main home screen UI and logic.
        - `NYTViewModel.kt`: ViewModel for handling New York Times data and business logic.
        - `NYTViewModelFactory.kt`: Factory for creating NYTViewModel instances.
        - `NewsDetailScreen.kt`: UI and logic for displaying detailed news articles.
        - `NewsViewModel.kt`: ViewModel for managing news data and business logic.
        - `PageScreen.kt`: Handles pagination or a specific page view in the home section.
    - `login/`
        - `LoginScreen.kt`: Login UI, handles user input and authentication logic via presenter.
    - `components/`: (Currently empty, intended for reusable UI components.)
- `app/src/main/java/com/example/news/presenter/`
    - `AuthPresenter.kt`: Interfaces for authentication logic (login/register, view attachment).
    - `NYTPresenter.kt`: Loads New York Times articles via NYTRepository.
    - `NewsPresenter.kt`: Interfaces for loading news and view attachment.
    - `impl/`
        - `AuthPresenterImpl.kt`: Implements AuthPresenter, handles login/register via AuthRepository.
        - `NewsPresenterImpl.kt`: Implements NewsPresenter, loads news from NewsRepository.
- `app/src/main/java/com/example/news/navigation/`
    - `NavGraph.kt`: Sets up the app's navigation graph, defines routes and connects screens.
- `app/src/main/res/`: UI resources (layouts, drawables, values, etc.)

## 2. Data Layer
- `app/src/main/java/com/example/news/model/model/`
    - `BaseArticle.kt`, `NYTArticle.kt`, `NYTResponse.kt`, `NewsArticle.kt`, `NewsResponse.kt`, `User.kt`: Data classes for articles, API responses, and user info.
- `app/src/main/java/com/example/news/model/network/`
    - `NYTAPIService.kt`, `NYTApiClient.kt`, `NewsAPIClient.kt`, `NewsAPIService.kt`: API service interfaces and clients for network communication.
- `app/src/main/java/com/example/news/model/repository/`
    - `AuthRepository.kt`: Handles authentication logic and data persistence.
    - `NYTRepository.kt`: Fetches New York Times articles from the network.
    - `NewsRepository.kt`: Fetches general news articles from the network or local storage.

## 3. Domain Layer (if present)
- Not explicitly separated, but business logic may be in presenters or repositories.

## 4. Test Layer
- `app/src/test/java/`: Unit tests
- `app/src/androidTest/java/`: Instrumentation tests

## 5. Build & Configuration Layer
- `/` (project root): Build scripts and configuration files
- `app/`: App module build scripts and configs
- `gradle/`: Gradle wrapper and version catalogs
- `build/`: Build outputs and reports
- `app/build/`: App module build outputs

## 6. CI/CD & Documentation
- `.github/instructions/`: Project instructions and documentation

---

> Note: The actual subfolders for data and domain layers depend on the project's architecture. This list now reflects the real structure of your codebase, including model, network, and repository folders, with a breakdown of the main files and their functions.
