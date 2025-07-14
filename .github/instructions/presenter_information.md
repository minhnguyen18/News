# Guidelines for Structuring Presenter Classes (Without ViewModel)

This guide outlines best practices for creating presenter classes to load news from a repository and handle logic and data persistence, following the structure and conventions of NewsPresenter, but without using ViewModel.

---

## 1. Purpose
- The presenter acts as an intermediary between the view (UI) and the repository (data source).
- It handles business logic, data fetching, and updates the view via an interface.
- It manages data persistence and state internally, not through ViewModel.

## 2. Class Structure
- Name the presenter according to its feature (e.g., `NewsPresenter`, `SportPresenter`).
- Place it in the appropriate package, e.g., `app/src/main/java/com/example/news/presenter/`.
- Define a view interface (e.g., `NewsView`) with methods for updating the UI (e.g., `showNews`, `showError`).
- The presenter should have methods for loading data, attaching/detaching the view, and handling user actions.

**Example:**
```kotlin
interface NewsView {
    fun showNews(articles: List<NewsArticle>)
    fun showError(message: String)
}

interface NewsPresenter {
    fun loadNews()
    fun attachView(view: NewsView)
    fun detachView()
}

class NewsPresenterImpl(private val repository: NewsRepository) : NewsPresenter {
    private var view: NewsView? = null

    override fun attachView(view: NewsView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun loadNews() {
        try {
            val articles = repository.getNews()
            view?.showNews(articles)
        } catch (e: Exception) {
            view?.showError("Failed to load news")
        }
    }
}
```
