import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 36
                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {

                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
                add("implementation", libs.findLibrary("androidx.compose.ui").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.compose.material3").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())

                add("implementation", "androidx.navigation:navigation-compose:2.8.0")
                add("implementation", "io.coil-kt:coil-compose:2.7.0")
                add("implementation", "androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")

                add("implementation", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
                add("implementation", "com.squareup.retrofit2:retrofit:2.9.0")
                add(
                    "implementation",
                    "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
                )

                add("implementation", "com.google.dagger:hilt-android:2.57.2")
                add("ksp", "com.google.dagger:hilt-android-compiler:2.57.2")
                add("implementation", "androidx.hilt:hilt-navigation-compose:1.2.0")

                add("implementation", "androidx.compose.material:material-icons-core:1.7.8")
                add("implementation", "androidx.compose.material:material-icons-extended:1.7.8")

                add("testImplementation", "junit:junit:4.13.2")
                add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
                add("testImplementation", "io.mockk:mockk:1.13.12")
                add("testImplementation", "app.cash.turbine:turbine:1.1.0")

                add("androidTestImplementation", "androidx.test.ext:junit:1.2.1")
                add(
                    "androidTestImplementation",
                    platform(libs.findLibrary("androidx.compose.bom").get())
                )
                add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
                add("debugImplementation", "androidx.compose.ui:ui-test-manifest")

            }
        }
    }
}