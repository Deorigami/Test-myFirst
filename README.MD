# News App - Compose Multiplatform (Android Focused)

A clean, modern news application built with **Compose Multiplatform**. While the project is designed for cross-platform compatibility, the current implementation focuses on **Android**, delivering a high-quality, native-feeling experience. This app demonstrates advanced development practices, including offline-first data handling, shared business logic, and modular architecture.

## 🌟 Key Features

- **Splash Screen**: Professional entry point with smooth transition.
- **Top Headlines Page**:
  - Fetches the latest **Technology** news from the **US**.
  - **Offline Mode**: Automatically caches news to a local database. If the network is unavailable, it displays the last successfully fetched headlines.
  - **Pagination**: Efficiently loads 5 items per page as you scroll.
  - **Pull to Refresh / Manual Refresh**: Refreshes data and updates the local cache when a network is available.
- **Search Page**:
  - Real-time searching using the Everything API.
  - Supports pagination for search results.
- **Bookmark System**:
  - Save articles locally using **Room Database**.
  - Consistent bookmark state across all screens (Headlines, Search, and Detail).
  - Dedicated **Bookmarks Page** to manage saved news.
- **News Detail Screen**:
  - Comprehensive view of the article content.
  - Integrated bookmarking functionality.

## 🏗️ Architecture (MVVM + Clean Architecture)

The project is built with a focus on **Separation of Concerns** and **Scalability**:

- **MVVM Pattern**: Uses `ViewModel` (ScreenModel) to manage UI state and handle events.
- **Clean Architecture**: Business logic is encapsulated in **Use Cases**, ensuring the domain layer is independent of data sources.
- **Modularization**: 
  - `:composeApp`: UI assembly and navigation.
  - `:features`: Feature-specific logic (`feature_headlines`, `feature_search`, `feature_bookmarks`).
  - `:services`: Data layer (`service_news`) handling Remote (Ktorfit) and Local (Room) sources.
  - `:cores`: Shared infrastructure (`core_service`, `core_feature`, `components`).

## 🛠️ Technology Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **UI Framework** | Compose Multiplatform (Material 3) |
| **Networking** | [Ktor](https://ktor.io/) & [Ktorfit](https://github.com/Foso/Ktorfit) |
| **Database** | [Room Multiplatform](https://developer.android.com/kotlin/multiplatform/room) |
| **Dependency Injection** | [Koin](https://insert-koin.io/) (with KSP Annotations) |
| **Image Loading** | [Coil3](https://coil-kt.github.io/coil/) |
| **Concurrency** | Kotlin Coroutines & Flow |
| **Logging** | [Kermit](https://github.com/touchlab/Kermit) |

## � Optional Implementations
Beyond the core requirements, this project includes several "nice-to-have" features to demonstrate professional proficiency:
- **Advanced Networking**: Uses **Ktor** and **Ktorfit** for type-safe API requests, providing an experience similar to Retrofit but with multiplatform support.
- **Dependency Injection**: Fully integrated with **Koin Annotations** and **KSP** for robust, compile-time checked dependency management.
- **Testing**: Includes **Unit Tests** for core business logic (Repository and Use Cases) and **Instrumentation/UI Tests** (using Compose UI Test) for core components like `NewsArticleItem` to ensure UI stability and event handling.

## �🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Android Studio Ladybird or newer
- Android SDK installed

### Configuration
The app uses a default API key for convenience. To use your own:
1. Get a free API key from [NewsAPI.org](https://newsapi.org).
2. Update the `apiKey` variable in `services/service_news/src/commonMain/kotlin/app/tktn/service_news/data/repository/NewsRepositoryImpl.kt`.

### Running the App
- **Android**: Open in Android Studio and run the `composeApp` configuration.
- **Desktop**: Run `./gradlew :composeApp:run` in the terminal.

## 📝 Implementation Notes
- **Pagination**: Implemented using a custom scroll listener in Compose, triggering page loads of 5 items.
- **Connectivity**: Real-time monitoring of network status to toggle between online and offline modes automatically.
- **Persistence**: Used Room for both caching (Headlines) and user data (Bookmarks), ensuring data integrity via a single NewsArticleEntity.

---
*Developed as a technical assessment for Android/KMP development.*
