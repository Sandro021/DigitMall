plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    // We need these so we can access Android and Kotlin configurations inside our custom plugin
    compileOnly("com.android.tools.build:gradle:8.13.2")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}

gradlePlugin {
    plugins {
        register("androidFeature") {
            id = "digitmall.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = "digitmall.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}