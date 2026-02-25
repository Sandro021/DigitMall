plugins {
    id("digitmall.android.feature")
}

android {
    namespace = "com.example.auth"

}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics")

    implementation(projects.core.ui)
    implementation(projects.feature.shopFeed)
}