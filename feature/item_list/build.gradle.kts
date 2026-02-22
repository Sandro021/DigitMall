plugins {
    id("digitmall.android.feature")
}

android {
    namespace = "com.example.item_list"

}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.feature.cart)
}