# Guidelines for Structuring Sport API Layer Classes (NewsAPI)

## 1. API Service Interface
- Create a dedicated API service interface (e.g., `SportAPIService`) similar to `NewsAPIService`.
- Define endpoint functions for fetching sports news, using appropriate query parameters (e.g., category="sports").
- Use Retrofit annotations (`@GET`, `@Query`) to specify endpoints and parameters.

**Example:**
```kotlin
interface SportAPIService {
    @GET("v2/top-headlines")
    suspend fun getSportsNews(
        @Query("category") category: String = "sports",
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
```

## 2. API Client
- Create a singleton object (e.g., `SportAPIClient`) to provide the Retrofit instance and the `SportAPIService` implementation.
- Use the same base URL as `NewsAPIClient`.

**Example:**
```kotlin
object SportAPIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: SportAPIService = retrofit.create(SportAPIService::class.java)
}
```


---

By following these guidelines, your sport API layer will be modular, testable, and consistent with the existing NewsAPI integration.
