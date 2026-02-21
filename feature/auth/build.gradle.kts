plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.auth"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core Android KTX extensions for easier Kotlin development (like Context/SharedPreferences extensions)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Connects Kotlin Coroutines to Android lifecycle components (allows lifecycleScope.launch)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Connects Jetpack Compose to standard Android Activities (provides setContent { })
    implementation(libs.androidx.activity.compose)

    // Bill of Materials (BOM) for Compose: ensures all Compose libraries use compatible versions automatically
    implementation(platform(libs.androidx.compose.bom))

    // Core foundational framework for Jetpack Compose UI
    implementation(libs.androidx.compose.ui)

    // Low-level graphics APIs for Jetpack Compose (canvas, drawing tools)
    implementation(libs.androidx.compose.ui.graphics)

    // Allows you to use the @Preview annotation to see Compose UI in Android Studio
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Provides Material Design 3 components and theming for Jetpack Compose
    implementation(libs.androidx.compose.material3)

    // Standard framework for writing basic local unit tests
    testImplementation(libs.junit)

    // Extensions to run JUnit tests on Android devices/emulators
    androidTestImplementation(libs.androidx.junit)

    // Framework for writing automated UI tests (simulating clicks, typing, etc.)
    androidTestImplementation(libs.androidx.espresso.core)

    // BOM for Compose, but specifically applied to the Android test environment
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Tools to test Jetpack Compose UI components specifically (finding nodes, clicking)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug tool that lets Android Studio inspect your Compose layout hierarchy
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Required to run Compose UI tests in isolation (provides an empty activity for tests)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Standard Jetpack Navigation library tailored for moving between Compose screens
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Bill of Materials (BOM) for Firebase: keeps all Firebase dependencies on compatible versions
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    // Firebase tool to track user behavior, screen views, and events in your app
    implementation("com.google.firebase:firebase-analytics")

    // Firebase tool to handle user authentication (email/password, Google sign-in, etc.)
    implementation("com.google.firebase:firebase-auth-ktx")

    // Classic Google Material Design components (often needed if you bridge View and Compose systems)
    implementation("com.google.android.material:material:1.13.0")

    // Extensions for working with Fragments in Kotlin (e.g., viewModels() delegate)
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    // Coroutine support for ViewModels (provides viewModelScope.launch)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")

    // Allows you to use newer Java 8+ APIs (like java.time) on older Android devices (API < 26)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    // JetBrains official library for parsing JSON into Kotlin data classes
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Interceptor to print out all your Retrofit network requests and responses to the Logcat
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Core I/O library used by OkHttp and Retrofit for reading/writing data
    implementation("com.squareup.okio:okio:3.9.1")

    // The standard library for making HTTP network requests and REST API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // (Note: This is an older/duplicate version of lifecycle-runtime-ktx added at the top)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

    // The main engine for running asynchronous, non-blocking code (Coroutines) on Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // An image loading library (older alternative to Coil, usually used with XML Views)
    implementation("com.github.bumptech.glide:glide:5.0.5")

    // Bridge that allows Retrofit to automatically use Kotlinx Serialization to parse JSON
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Dagger Hilt framework for Dependency Injection (providing ViewModels, Repositories, etc.)
    implementation("com.google.dagger:hilt-android:2.57.2")

    // Handles fetching and displaying large lists of data in small chunks (pagination)
    implementation("androidx.paging:paging-runtime:3.2.1")

    // Compiler that processes your @Inject and @AndroidEntryPoint Hilt annotations
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")

    // Official Android API for customizing the launch screen before your first Activity loads
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Integrates Google Maps directly into your app
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Access to the device's GPS and location services to get coordinates
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // Utilities for Google Maps (clustering markers, calculating distances, heatmaps)
    implementation("com.google.maps.android:android-maps-utils:3.8.2")

    // Allows Hilt to scope ViewModels specifically to Compose Navigation routes using hiltViewModel()
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Alpha libraries for Jetpack Navigation 3 (the upcoming experimental navigation system)
    implementation("androidx.navigation3:navigation3-runtime:1.1.0-alpha02")
    implementation("androidx.navigation3:navigation3-ui:1.1.0-alpha02")

    // Modern, Kotlin-first image loading library (backend logic)
    implementation("io.coil-kt:coil:2.6.0")

    // Extension for Coil that provides the AsyncImage composable to load images from URLs
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Utilities for animating composables and state changes in Jetpack Compose
    implementation("androidx.compose.animation:animation")

    // Testing utilities for Coroutines (provides runTest and unconfined test dispatchers)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

    // A popular mocking framework for Kotlin to fake dependencies in unit tests
    testImplementation("io.mockk:mockk:1.13.12")

    // A lightweight testing library specifically for verifying Kotlin Flows emit correct values
    testImplementation("app.cash.turbine:turbine:1.1.0")

    // Unlocks all standard Material Design icons (e.g., Icons.Default.ShoppingCart) for Compose
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // Media3 ExoPlayer: The standard modern player for streaming audio and video in Android
    implementation("androidx.media3:media3-exoplayer:1.4.1")

    // Pre-built UI controls for ExoPlayer (play, pause, timeline scrubbers)
    implementation("androidx.media3:media3-ui:1.4.1")

    // Common components shared across the Media3 audio/video architecture
    implementation("androidx.media3:media3-common:1.4.1")

    // Modern, asynchronous replacement for SharedPreferences (saves simple key-value data)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Foundational Compose tools (includes HorizontalPager, VerticalPager, and basic gestures)
    implementation("androidx.compose.foundation:foundation:1.7.5")

    // Allows you to create and access ViewModels directly inside your Composable functions
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // DUPLICATE: You already have this listed above! Commented out to prevent build issues.
    // implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Connects to Google Pay / Google Wallet APIs for transactions and passes
    implementation("com.google.android.gms:play-services-wallet:19.4.0")

    implementation(projects.core.ui)
}