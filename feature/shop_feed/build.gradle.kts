plugins {
    id("digitmall.android.feature")

}

android {
    namespace = "com.example.shop_feed"

}

dependencies {

    implementation(projects.core.ui)
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")

}