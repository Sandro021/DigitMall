pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "DigitMall"
include(":app")
include(":feature")
include(":core")
include(":core:ui")
include(":core:di")
include(":feature:shop_feed")
include(":feature:item_list")
include(":feature:cart")
include(":feature:item_feed")
include(":feature:auth")
include(":feature:feed")
include(":core:data")
include(":feature:payment")
include(":feature:profile")
include(":feature:company_profile")
include(":feature:item")
