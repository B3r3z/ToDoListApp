# ToDo List App

A simple ToDo List application for Android, built using modern Android development tools. This app allows users to create, manage, and track their tasks.

## Features

*   **Add Tasks:** Easily add new tasks to your to-do list.
*   **Edit Tasks:** Modify existing tasks, including their name and description.
*   **Delete Tasks:** Remove tasks that are no longer needed.
*   **Mark as Complete:** Mark tasks as completed to keep track of your progress.
*   **Persistent Storage:** Tasks are saved locally on the device using Room database.
*   **User-Friendly Interface:** Built with Jetpack Compose for a modern and reactive UI.

## Technologies Used

*   **Kotlin:** The primary programming language for Android development.
*   **Jetpack Compose:** Android's modern toolkit for building native UI.
*   **Room Persistence Library:** Provides an abstraction layer over SQLite to allow for more robust database access.
*   **ViewModel:** Manages UI-related data in a lifecycle-conscious way.
*   **Coroutines:** For managing background threads and asynchronous operations.
*   **Navigation Component:** For handling in-app navigation.
*   **Material Design 3:** For UI components and styling.

## Setup and Installation

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Open in Android Studio:**
    Open Android Studio, select "Open an Existing Project", and navigate to the cloned project directory.
3.  **Build the project:**
    Android Studio should automatically sync the Gradle files. If not, click on "Sync Project with Gradle Files" (the elephant icon with a down arrow).
4.  **Run the app:**
    Select an emulator or connect a physical device and click the "Run" button (green play icon).

## How to Build

The project uses Gradle as its build system. You can build the project from Android Studio or by using the Gradle wrapper in the command line:

*   **Debug build:**
    ```bash
    ./gradlew assembleDebug
    ```
*   **Release build:**
    ```bash
    ./gradlew assembleRelease
    ```

The APK files will be located in `/app/build/outputs/apk/`.

## Project Structure

*   `app/src/main/java/com/example/todolistapp/`: Contains the main source code.
    *   `data/`: Data models (e.g., `Task.kt`).
    *   `database/`: Room database setup (`ToDoDatabase.kt`, `TaskDao.kt`, `ToDoRepository.kt`).
    *   `ui/`: Composable UI screens and components (`ToDoListScreen.kt`, `AddEditScreen.kt`).
    *   `MainActivity.kt`: The main entry point of the application.
    *   `ToDoListApp.kt`: Main application composable, handling navigation and scaffold.
*   `app/src/main/res/`: Android resources (layouts, drawables, strings, etc.).
*   `build.gradle.kts` (project level and app level): Gradle build scripts.


