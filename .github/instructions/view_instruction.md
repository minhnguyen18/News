# Guidelines for Structuring View Layer Classes

## 1. File Naming and Placement
- Place each screen or major UI component in its own file, named according to its function (e.g., `HomeScreen.kt`, `SportScreen.kt`).
- Organize files into feature-based folders (e.g., `home/`, `login/`, `components/`).

## 2. Composable Functions
- Use `@Composable` functions for all UI elements.
- The main screen composable should be named after the file (e.g., `HomeScreen`, `SportScreen`).
- Break down complex screens into smaller, reusable composables (e.g., `FlatSearchBar`, `CategoryIcon`, `NewsItem`).

## 3. State Management
- Use ViewModels to manage UI state and business logic.
- Pass ViewModel instances as parameters to composables when needed.
- Use `by viewModel.articles` or similar state delegation for reactive UI updates.

## 4. UI Structure
- Use `LazyColumn` or `LazyRow` for lists.
- Use `Row`, `Column`, and `Box` for layout structure.
- Use `Scaffold` for screens with top/bottom bars.
- Use `Modifier` for styling, padding, and layout control.

## 5. Navigation
- Use the Navigation component for screen transitions.
- Pass navigation callbacks (e.g., `onReadMoreClick`, `onLogoutClick`) as parameters.

## 6. Theming and Resources
- Use theme files (`Color.kt`, `Theme.kt`, `Type.kt`) for consistent styling.
- Reference resources (icons, images, strings) via `R.drawable` or `R.string`.

## 7. Reusability
- Place shared UI elements in a `components/` folder for reuse across screens.
- Reuse composables like `FlatSearchBar`, `CategoryIcon`, `SwipeableNewsItem`, and `NewsItem`.

## 8. Documentation
- Add KDoc comments to composables and public functions for clarity.
- Keep code readable and well-organized.

---

By following these guidelines, your view layer will be modular, maintainable, and consistent across the project.

