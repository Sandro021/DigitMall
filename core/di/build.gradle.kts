plugins {
    id("digitmall.android.library")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.di"

}

dependencies {
    
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okio:okio:3.9.1")

    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation(projects.feature.shopFeed)
    implementation(projects.feature.cart)
    implementation(projects.feature.itemList)
    implementation(projects.feature.itemFeed)
    implementation(projects.feature.auth)
    implementation(projects.feature.feed)
    implementation(projects.core.data)
    implementation(projects.feature.profile)
    implementation(projects.feature.companyProfile)

}