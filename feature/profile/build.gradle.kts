plugins {
    id("digitmall.android.feature")
}

android {
    namespace = "com.example.profile"

}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage")
    implementation(libs.firebase.storage)

    implementation(projects.feature.auth)
    implementation(projects.core.data)
    implementation(projects.core.ui)


}