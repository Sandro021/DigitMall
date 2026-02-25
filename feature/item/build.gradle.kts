plugins {
    id("digitmall.android.feature")
}

android {
    namespace = "com.example.item_list"

}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.feature.cart)
    implementation(projects.core.data)
    implementation(projects.feature.profile)
}