plugins {

    id("digitmall.android.feature")

}

android {
    namespace = "com.example.cart"

}
dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.data)

}