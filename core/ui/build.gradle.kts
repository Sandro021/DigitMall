plugins {
    id("digitmall.android.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.ui"

}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
}