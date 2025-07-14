# Guidelines for Structuring Repository Classes

This guide outlines best practices for creating repository classes to connect with APIs, following the structure and conventions of NewsRepository.

---

## 1. Purpose
- The repository acts as a single source of truth for fetching, caching, and providing data to the view and viewmodel layers.
- It abstracts the details of API calls and data sources from the UI.

## 2. Class Structure
- Name the repository according to its feature (e.g., `SportRepository`, `NewsRepository`).
- Place it in the appropriate package, e.g., `app/src/main/java/com/example/news/model/repository/`.
- Inject or instantiate the API service (e.g., `SportAPIService`, `NewsAPIService`) in the constructor.

## 3. API Integration
- Use the API service to fetch data, similar to how NewsRepository uses NewsAPIService.
- Expose suspend functions for fetching data, returning a list of articles or a response model.
- Handle exceptions and return empty lists or error states as needed.

**Example:**
```kotlin
class SportRepository(private val apiService: SportAPIService) {
    suspend fun fetchSportsNews(apiKey: String): List<NewsArticle> {
        return try {
            apiService.getSportsNews(apiKey = apiKey).articles
        } catch (e: Exception) {
            emptyList()
        }
    }
}
```

## 4. Usage
- Use the repository in your ViewModel (e.g., `SportViewModel`) to fetch and expose data to the UI.
- Keep business logic and data transformation in the repository, not in the UI layer.

## 5. Best Practices
- Keep repository methods focused and single-purpose.
- Handle API errors gracefully and log them if needed.
- Write unit tests for repository methods.

---

By following these guidelines, your repository classes will be modular, testable, and consistent with the existing NewsRepository pattern.
