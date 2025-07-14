# Guidelines for Structuring Navigation Classes and Logic

## 1. Navigation Graph Structure
- Use a single `NavGraph.kt` file to define all navigation routes and destinations for your app.
- Use sealed classes or enums to represent screen routes (e.g., `Screen.Home`, `Screen.Sport`).
- Define a `@Composable` function (e.g., `AppNavGraph`) that sets up the `NavHost` and all composable destinations.

## 2. Adding New Screens
- Add a new route to your sealed class (e.g., `object Sport : Screen("sport")`).
- In `NavGraph.kt`, add a new `composable` entry for the new screen, passing required parameters.
- Use navigation callbacks (e.g., `onReadMoreClick`, `onLogoutClick`) to handle navigation events.

## 3. Integrating Navigation in HomeScreen
- Use a `NavController` in your HomeScreen to navigate between screens.
- Add navigation UI elements (e.g., bottom navigation bar, buttons) that call `navController.navigate(Screen.Sport.route)`.
- Pass the `NavController` as a parameter to composables that need to trigger navigation.

## 4. Example: Adding SportScreen Navigation
**In NavGraph.kt:**
```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Sport : Screen("sport")
    // ...other screens
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Sport.route) { SportScreen(navController) }
        // ...other composables
    }
}
```

**In HomeScreen.kt:**
```kotlin
@Composable
fun HomeScreen(navController: NavHostController) {
    // ...existing code...
    Button(onClick = { navController.navigate(Screen.Sport.route) }) {
        Text("Go to Sport")
    }
    // ...existing code...
}
```

## 5. Best Practices
- Keep navigation logic out of business logic and UI state management.
- Use type-safe route definitions (sealed classes or enums).
- Pass only necessary arguments between screens.
- Keep navigation code centralized in the navigation graph.

---

By following these guidelines, your navigation will be scalable, maintainable, and easy to extend as your app grows.
