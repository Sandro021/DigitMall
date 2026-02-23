plugins {
    id("digitmall.android.feature")
}

android {
    namespace = "com.example.payment"

}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation("com.google.android.gms:play-services-wallet:19.4.0")

    implementation("com.google.pay.button:compose-pay-button:1.1.0")

    implementation(projects.core.ui)
    implementation(libs.androidx.junit.ktx)

}
