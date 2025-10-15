pluginManagement {
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

rootProject.name = "MyPlants"
include(":app")
include(":core:db")
include(":core:di")
include(":core:ui")
include(":domain")
include(":data:local")
include(":data:backup")
include(":data:photo")
include(":feature:main")
include(":feature:plant_detail")
include(":feature:add_plant")
include(":feature:settings")
include(":feature:genus_detail")
