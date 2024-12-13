Project Description: NewsApp_Zonak
The NewsApp_Zonak is a modern Android application designed for browsing news across multiple categories, ensuring a seamless, reliable, and robust user experience. The app fetches news data from the NewsAPI and incorporates cutting-edge Android technologies, architectural patterns, and comprehensive testing strategies.

Key Features:
Dynamic News Fetching:
Retrieves top headlines and category-based news dynamically using Retrofit for network operations.
Offline Support:
Implements local caching with Room Database to store news articles by category, allowing users to access headlines even without an active internet connection.
State Management:
Utilizes the Model-View-Intent (MVI) architecture pattern to centralize application state, ensuring robust and predictable behavior across screens.
Dependency Injection:
Uses Dagger Hilt for clean dependency management, enhancing testability and scalability.
Optimized Data Flow:
Separates data handling into a repository layer:
Fetches remote news articles from INewsRemoteDs.
Stores and retrieves local articles using INewsLocalDs.
Maps data using ArticleMapper for seamless domain-to-database-to-DTO conversions.
Testing Strategy:
To ensure the reliability and performance of the application, multiple layers of testing are implemented:

Unit Testing:

Validates the logic of repository methods like getTopHeadlines() and getLocalTopHeadlinesByCategory().
Mocked data sources (INewsRemoteDs and INewsLocalDs) are used to simulate various scenarios.
Turbine is used for testing Kotlin Flows, ensuring correctness and reliability of emitted data streams.
UI Testing:

Verifies user interface components, such as news lists and category selections, using Jetpack Compose Testing tools.
Ensures proper rendering and behavior across different device configurations.
End-to-End Testing:

Tests the complete application flow, including fetching news, caching, and navigating between screens, using tools like UI Automator.
Simulates real user interactions to ensure a seamless experience.
Technologies and Tools Used:
Programming Language: Kotlin
Networking: Retrofit
Database: Room
Dependency Injection: Dagger Hilt
Architecture: MVI (Model-View-Intent)
Testing Tools:
Unit Testing: Turbine, JUnit
UI Testing: Jetpack Compose Testing
End-to-End Testing: UI Automator
This project demonstrates expertise in modern Android development practices, robust architecture, and rigorous testing methodologies.

